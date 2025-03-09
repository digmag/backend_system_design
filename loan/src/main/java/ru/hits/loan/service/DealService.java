package ru.hits.loan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.loan.entity.DealEntity;
import ru.hits.loan.feignClient.BillClient;
import ru.hits.loan.repository.DealRepository;
import ru.hits.loan.repository.LoanRepository;
import ru.hits.loan.service.interfaces.IDealService;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealService implements IDealService {
    private final LoanRepository loanRepository;
    private final DealRepository dealRepository;
    private final BillClient billClient;

    @Transactional
    @Override
    public BillResponseDTO createCreditBill(CreditBillCreateDTO billCreateDTO, Authentication authentication) {
        if(LocalDate.now().isAfter(billCreateDTO.getTo())){
            throw new BadRequestException("Дата окончания кредита не может быть позже");
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
                billCreateDTO.getAmount()*actualLoan.getPercent()/100,
                billCreateDTO.getTo()
        );
        billCreateDTO.setAmount(dealEntity.getSum());
        dealRepository.save(dealEntity);
        return billClient.createCreditBill(billCreateDTO, dealEntity.getId(), user.getId());
    }

    @Override
    public void scheduleTransactions() {

    }
}
