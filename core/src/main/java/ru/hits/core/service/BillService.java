package ru.hits.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.bill.*;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.core.entity.BillEntity;
import ru.hits.core.entity.TransactionEntity;
import ru.hits.core.feignClient.UserClient;
import ru.hits.core.repository.BillRepository;
import ru.hits.core.repository.TransactionRepository;
import ru.hits.core.service.interfaces.IBillService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService {
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public BillResponseDTO create(BillCreateDTO billCreateDTO, Authentication authentication) {
        JwtUserData user = (JwtUserData) authentication.getPrincipal();
        BillEntity bill = new BillEntity(
                UUID.randomUUID(),
                user.getId(),
                0.0,
                billCreateDTO.getType(),
                Status.OPEN,
                billCreateDTO.getName()
        );
        billRepository.save(bill);
        return new BillResponseDTO(
                bill.getId(),
                user.getId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus(),
                bill.getName()
        );
    }

    @Transactional
    @Override
    public void closeBill(UUID id, Authentication authentication) {
        var user = (JwtUserData) authentication.getPrincipal();

        BillEntity bill = billRepository.findById(id).orElse(null);
        if(bill == null){
            throw new NotFoundException("Счета не существует");
        }
        if(!user.getId().equals(bill.getUserId())){
            throw new ForbiddenException("Невозможно закрыть счет");
        }
        bill.setStatus(Status.CLOSED);
        billRepository.save(bill);
    }

    @Transactional
    @Override
    public TransactionResponseDTO topUp(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication) {
        var bill = billRepository.findById(id).orElse(null);
        if(bill == null){
            throw new BadRequestException("Счета не существует");
        }
        if(bill.getStatus() != Status.OPEN){
            throw new ForbiddenException("Счет закрыт");
        }
        BillResponseDTO billTo = new BillResponseDTO(
                bill.getId(),
                bill.getUserId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus(),
                bill.getName()
        );
        if(transactionCreateDTO.getAmount()<=0){
            throw new BadRequestException("Сумма перевода должна быть больше нуля");
        }
        bill.setAmount(bill.getAmount() + transactionCreateDTO.getAmount());
        billRepository.save(bill);
        TransactionEntity transactionEntity = createTransaction(null, bill, transactionCreateDTO.getAmount());
        return new TransactionResponseDTO(
                transactionEntity.getId(),
                null,
                billTo,
                transactionEntity.getAmount()
        );
    }

    @Override
    public TransactionResponseDTO topDown(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication) {
        var bill = billRepository.findById(id).orElse(null);
        if(bill == null){
            throw new BadRequestException("Счета не существует");
        }
        if(bill.getStatus() != Status.OPEN){
            throw new ForbiddenException("Счет закрыт");
        }
        BillResponseDTO billTo = new BillResponseDTO(
                bill.getId(),
                bill.getUserId(),
                bill.getAmount(),
                bill.getType(),
                bill.getStatus(),
                bill.getName()
        );
        if(transactionCreateDTO.getAmount()<=0){
            throw new BadRequestException("Сумма перевода должна быть больше нуля");
        }
        if(bill.getAmount() < transactionCreateDTO.getAmount()){
            throw new BadRequestException("Сумма перевода не дложна привышать суммы на счете");
        }
        bill.setAmount(bill.getAmount() - transactionCreateDTO.getAmount());
        billRepository.save(bill);
        TransactionEntity transactionEntity = createTransaction(bill, null, transactionCreateDTO.getAmount());
        return new TransactionResponseDTO(
                transactionEntity.getId(),
                null,
                billTo,
                transactionEntity.getAmount()
        );
    }

    @Override
    public List<BillResponseDTO> getMyBills(Authentication authentication) {
        var user = (JwtUserData) authentication.getPrincipal();
        List<BillEntity> billEntities = billRepository.findAllByUserId(user.getId());
        return billEntities.stream().map(bill ->
                new BillResponseDTO(
                        bill.getId(),
                        bill.getUserId(),
                        bill.getAmount(),
                        bill.getType(),
                        bill.getStatus(),
                        bill.getName()
                        )).toList();
    }

    @Transactional
    @Override
    public TransactionResponseDTO transaction(UUID from,
                                              UUID to,
                                              TransactionCreateDTO transactionCreateDTO,
                                              Authentication authentication) {
        var user = (JwtUserData) authentication.getPrincipal();
        BillEntity billFrom = billRepository.findById(from).orElse(null);
        BillEntity billTo = billRepository.findById(to).orElse(null);
        if(billFrom == null || billTo == null){
            throw new BadRequestException("Счет не существует");
        }
        if(billFrom.getUserId() != user.getId()){
            throw new ForbiddenException("Счет не принадлежит пользователю");
        }
        if(billFrom.getStatus() != Status.OPEN || billTo.getStatus() != Status.OPEN){
            throw new ForbiddenException("Счета закрыты для перевода");
        }
        if(billFrom.getAmount()<transactionCreateDTO.getAmount()){
            throw new BadRequestException("На счете недостаточно средств");
        }
        billFrom.setAmount(billFrom.getAmount() - transactionCreateDTO.getAmount());
        billTo.setAmount(billTo.getAmount() + transactionCreateDTO.getAmount());
        billRepository.save(billFrom);
        billRepository.save(billTo);
        TransactionEntity transaction = createTransaction(billFrom, billTo, transactionCreateDTO.getAmount());
        return new TransactionResponseDTO(
                transaction.getId(),
                new BillResponseDTO(
                        transaction.getBillFrom().getId(),
                        transaction.getBillFrom().getUserId(),
                        transaction.getBillFrom().getAmount(),
                        transaction.getBillFrom().getType(),
                        transaction.getBillFrom().getStatus(),
                        transaction.getBillFrom().getName()
                ),
                new BillResponseDTO(
                        transaction.getBillTo().getId(),
                        transaction.getBillTo().getUserId(),
                        transaction.getBillTo().getAmount(),
                        transaction.getBillTo().getType(),
                        transaction.getBillTo().getStatus(),
                        transaction.getBillTo().getName()
                ),
                transaction.getAmount()
        );
    }

    @Override
    public List<TransactionResponseDTO> transactions(UUID id) {
        return null;
    }

    @Override
    public void closeBillInOneTap(UUID id) {
        
    }

    private TransactionEntity createTransaction(BillEntity billFrom, BillEntity billTo, Double amount){
        TransactionEntity transaction = new TransactionEntity(
                UUID.randomUUID(),
                billFrom,
                billTo,
                amount);
        return transactionRepository.save(transaction);
    }
}
