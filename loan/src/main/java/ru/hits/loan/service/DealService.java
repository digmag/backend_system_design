package ru.hits.loan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.common.dtos.bill.TransactionCreateDTO;
import ru.hits.common.dtos.bill.TransactionMessageDTO;
import ru.hits.common.dtos.loan.DealResponseDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.loan.entity.DealEntity;
import ru.hits.loan.feignClient.BillClient;
import ru.hits.loan.producer.KafkaTransactionProducer;
import ru.hits.loan.repository.DealRepository;
import ru.hits.loan.repository.LoanRepository;
import ru.hits.loan.service.interfaces.IDealService;
import java.time.LocalDate;
import java.util.UUID;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
public class DealService implements IDealService {
    private final LoanRepository loanRepository;
    private final DealRepository dealRepository;
    private final BillClient billClient;
    private final KafkaTransactionProducer kafkaTransactionProducer;

    @Transactional
    @Override
    public BillResponseDTO createCreditBill(CreditBillCreateDTO billCreateDTO, Authentication authentication) {
        if(LocalDate.now().isAfter(billCreateDTO.getTo())){
            throw new BadRequestException("Дата окончания кредита не может быть позже");
        }
        if(billCreateDTO.getAmount() <=0){
            throw new BadRequestException("Нельзя создать отрицательную транзакцию");
        }
        var user = (JwtUserData) authentication.getPrincipal();
        var actualLoan = loanRepository.findByIsActive(true).orElse(null);
        if(actualLoan == null){
            throw new NotFoundException("Нет актуальных тарифов по кредитам");
        }
        if(!billClient.isBillExists(billCreateDTO.getLinkedBill())){
            throw new NotFoundException("Счета не существует");
        }
        DealEntity dealEntity = new DealEntity(
                UUID.randomUUID(),
                actualLoan,
                billCreateDTO.getLinkedBill(),
                billCreateDTO.getAmount()*actualLoan.getPercent()/100+billCreateDTO.getAmount(),
                billCreateDTO.getTo(),
                LocalDate.now()
        );
        billCreateDTO.setAmount(dealEntity.getSum());
        dealRepository.save(dealEntity);
        return billClient.createCreditBill(billCreateDTO, dealEntity.getId(), user.getId());
    }

    @Override
    public LoanResponseDTO getActual() {
        var loan = loanRepository.findByIsActive(true).orElse(null);
        return new LoanResponseDTO(
                loan.getId(),
                loan.getName(),
                loan.getPercent(),
                loan.getIsActive()
        );
    }

    public void scheduleTransactions() {
        var deals = dealRepository.findAll();
        deals.forEach(dealEntity -> {
            int defer = dealEntity.getDuring().until(dealEntity.getFrom()).getDays();
            double cent = Math.abs(dealEntity.getSum() / defer);
            var bill = billClient.getBill(dealEntity.getBillId());
            var masterBill = billClient.getMasterBillId();
            if (bill.getAmount() <= 0 && bill.getAmount()>=cent && dealEntity.getSum()>0) {
                dealEntity.setSum(dealEntity.getSum()-cent);
                dealRepository.save(dealEntity);
                TransactionMessageDTO message = new TransactionMessageDTO(
                        dealEntity.getBillId(),
                        masterBill,
                        cent
                );
                kafkaTransactionProducer.sendTransaction(message);
            } else {
                //billClient.closeCreditBill(dealEntity.getId());
            }
        });
    }

    @Override
    public DealResponseDTO getDeal(UUID id) {
        var deal = dealRepository.findById(id).orElse(null);
        return new DealResponseDTO(
                deal.getId(),
                deal.getBillId()
        );
    }
}
