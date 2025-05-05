package ru.hits.core.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import ru.hits.common.dtos.bill.*;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.core.entity.BillEntity;
import ru.hits.core.entity.TransactionEntity;
import ru.hits.core.repository.BillRepository;
import ru.hits.core.repository.TransactionRepository;
import ru.hits.core.service.interfaces.IIntegrationBillService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntegrationBillService implements IIntegrationBillService {
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;
    private final WsService wsService;
    @Override
    public List<BillResponseDTO> getUsersBills(UUID userId) {
        var bills = billRepository.findAllByUserId(userId);
        return bills.stream().map(bill ->
                new BillResponseDTO(
                        bill.getId(),
                        bill.getUserId(),
                        bill.getAmount(),
                        bill.getType(),
                        bill.getStatus(),
                        bill.getName()
                )).toList();
    }

    @Override
    public List<TransactionResponseDTO> getBillsTransactions(UUID billId) {
        var bill = billRepository.findById(billId).orElse(null);
        if(bill == null){
            throw new BadRequestException("Счета не существует");
        }
        var transactionsFrom = transactionRepository.findAllByBillFrom(bill);
        var transactionsTo = transactionRepository.findAllByBillTo(bill);
        transactionsFrom.addAll(transactionsTo);
        return transactionsFrom.stream().map(transaction -> {
            var billFrom = transaction.getBillFrom();
            var billTo = transaction.getBillTo();
            BillResponseDTO billFromDTO = null;
            BillResponseDTO billToDTO = null;
            if(billFrom != null){
                billFromDTO = new BillResponseDTO(
                        billFrom.getId(),
                        billFrom.getUserId(),
                        billFrom.getAmount(),
                        billFrom.getType(),
                        billFrom.getStatus(),
                        billFrom.getName()
                );
            }
            if(billTo != null) {
                billToDTO = new BillResponseDTO(
                        billTo.getId(),
                        billTo.getUserId(),
                        billTo.getAmount(),
                        billTo.getType(),
                        billTo.getStatus(),
                        billTo.getName()
                );
            }
            return new TransactionResponseDTO(
                    transaction.getId(),
                    billFromDTO,
                    billToDTO,
                    transaction.getAmount()
            );
        }).toList();
    }

    @Override
    public Boolean isBillExists(UUID billId) {
        var bill = billRepository.findById(billId).orElse(null);
        return bill!=null;
    }

    @Transactional
    @Override
    public void blockBill(UUID billId) {
        var bill = billRepository.findById(billId).orElse(null);
        if(bill == null){
            throw new NotFoundException("Счвета не существует");
        }
        bill.setStatus(Status.BLOCKED);
        billRepository.save(bill);
    }

    @Override
    public BillResponseDTO createCreditBill(UUID creditBillId, CreditBillCreateDTO billCreateDTO, UUID userId, UUID ik) {
        BillEntity clientBill = billRepository.findById(billCreateDTO.getLinkedBill())
                .orElseThrow(() -> new BadRequestException("Клиентский счет не найден"));

        BillEntity masterBill = billRepository.findByType(Type.MASTER)
                .orElseThrow(() -> new BadRequestException("Мастер-счет не найден"));

        double creditAmount = billCreateDTO.getAmount();

        if (masterBill.getAmount() < creditAmount) {
            throw new BadRequestException("На мастер-счете недостаточно средств");
        }

        masterBill.setAmount(masterBill.getAmount() - creditAmount);
        clientBill.setAmount(clientBill.getAmount() + creditAmount);

        billRepository.save(masterBill);
        billRepository.save(clientBill);

        BillEntity creditBill = new BillEntity(
                creditBillId,
                userId,
                -creditAmount,
                Type.CREDIT,
                Status.OPEN,
                billCreateDTO.getName()
        );

        billRepository.save(creditBill);

        return new BillResponseDTO(
                creditBill.getId(),
                creditBill.getUserId(),
                creditBill.getAmount(),
                creditBill.getType(),
                creditBill.getStatus(),
                creditBill.getName()
        );
    }

    @Transactional
    @Override
    @SneakyThrows
    public void transaction(UUID from, UUID to, TransactionCreateDTO transactionCreateDTO, UUID ik) {
        var bFrom = billRepository.findById(from).orElse(null);
        var bTo = billRepository.findById(to).orElse(null);
        TransactionEntity transaction = new TransactionEntity(
                UUID.randomUUID(),
                bFrom,
                bTo,
                transactionCreateDTO.getAmount()
        );
        if (bFrom.getAmount() < transactionCreateDTO.getAmount()) {
            throw new BadRequestException("Недостаточно средств на счете-отправителе");
        }
        bFrom.setAmount(bFrom.getAmount()-transactionCreateDTO.getAmount());
        bTo.setAmount(bTo.getAmount()+transactionCreateDTO.getAmount());
        wsService.sendMessage(transaction.getBillTo().getUserId(), transaction.getBillFrom().getUserId(),
                new TransactionResponseDTO(
                        transaction.getId(),
                        new BillResponseDTO(
                                bFrom.getId(),
                                bFrom.getUserId(),
                                bFrom.getAmount(),
                                bFrom.getType(),
                                bFrom.getStatus(),
                                bFrom.getName()
                        ),
                        new BillResponseDTO(
                                bTo.getId(),
                                bTo.getUserId(),
                                bTo.getAmount(),
                                bTo.getType(),
                                bTo.getStatus(),
                                bTo.getName()
                        ),
                        transaction.getAmount()
                )
        );
        billRepository.save(bFrom);
        billRepository.save(bTo);
        transactionRepository.save(transaction);
    }

    @Override
    public BillResponseDTO getCreditBill(UUID id) {
        var bill = billRepository.findById(id).orElse(null);
        return new BillResponseDTO(
                bill.getId(),
                bill.getUserId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus(),
                bill.getName()
        );
    }

    @Override
    public void closeCreditBill(UUID id) {
        var bill = billRepository.findById(id).orElse(null);
        bill.setStatus(Status.CLOSED);
        billRepository.save(bill);
    }

    @Override
    public UUID getMasterBillId() {
        var bill = billRepository.findByType(Type.MASTER)
                .orElseThrow(() -> new NotFoundException("Мастер-счёт не найден"));
        return bill.getId();
    }
}
