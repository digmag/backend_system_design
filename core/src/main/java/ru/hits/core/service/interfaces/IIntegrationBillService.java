package ru.hits.core.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;

import java.util.List;
import java.util.UUID;

@Service
public interface IIntegrationBillService {
    List<BillResponseDTO> getUsersBills(UUID userId);
    List<TransactionResponseDTO> getBillsTransactions(UUID billId);
    Boolean isBillExists(UUID billId);
    void blockBill(UUID billId);

    BillResponseDTO createCreditBill(UUID id, CreditBillCreateDTO billCreateDTO, UUID userId);
}
