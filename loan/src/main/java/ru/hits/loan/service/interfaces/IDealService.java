package ru.hits.loan.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;

import java.util.UUID;

@Service
public interface IDealService {
    BillResponseDTO createCreditBill(CreditBillCreateDTO billCreateDTO, Authentication authentication);
    void scheduleTransactions();
}
