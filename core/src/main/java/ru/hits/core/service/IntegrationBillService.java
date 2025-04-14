package ru.hits.core.service;

import lombok.RequiredArgsConstructor;
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
    public BillResponseDTO createCreditBill(UUID id, CreditBillCreateDTO billCreateDTO, UUID userId) {
        BillEntity myBill = billRepository.findById(billCreateDTO.getLinkedBill()).orElse(null);
        myBill.setAmount(myBill.getAmount()+billCreateDTO.getAmount());
        BillEntity bill = new BillEntity(
                id,
                userId,
                (-1)*billCreateDTO.getAmount(),
                Type.CREDIT,
                Status.OPEN,
                billCreateDTO.getName()
        );
        billRepository.save(bill);
        billRepository.save(myBill);
        return new BillResponseDTO(
                bill.getId(),
                bill.getUserId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus(),
                bill.getName()
        );
    }

    @Transactional
    @Override
    public void transaction(UUID from, UUID to, TransactionCreateDTO transactionCreateDTO) {
        var bFrom = billRepository.findById(from).orElse(null);
        var bTo = billRepository.findById(to).orElse(null);
        TransactionEntity transaction = new TransactionEntity(
                UUID.randomUUID(),
                bFrom,
                bTo,
                transactionCreateDTO.getAmount()
        );
        bFrom.setAmount(bFrom.getAmount()-transactionCreateDTO.getAmount());
        bTo.setAmount(bTo.getAmount()+transactionCreateDTO.getAmount());
        wsService.sendMessage(transaction.getBillTo().getUserId(), transaction.getBillFrom().getUserId(),
                new TextMessage(transaction.toString())
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
}
