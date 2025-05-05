package ru.hits.loan.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.common.dtos.loan.DealResponseDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;

import java.util.UUID;

@Service
public interface IDealService {
    BillResponseDTO createCreditBill(CreditBillCreateDTO billCreateDTO, Authentication authentication, UUID ik);
    LoanResponseDTO getActual();
    void scheduleTransactions();

    DealResponseDTO getDeal(UUID id);

}
