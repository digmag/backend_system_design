package ru.hits.core.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.*;

import java.util.List;
import java.util.UUID;

@Service
public interface IIntegrationBillService {
    List<BillResponseDTO> getUsersBills(UUID userId);
    List<TransactionResponseDTO> getBillsTransactions(UUID billId);
    Boolean isBillExists(UUID billId);
    void blockBill(UUID billId);

    BillResponseDTO createCreditBill(UUID id, CreditBillCreateDTO billCreateDTO, UUID userId, UUID ik);
    void transaction(UUID from,
                     UUID to,
                     TransactionCreateDTO transactionCreateDTO,
                     UUID ik);
    BillResponseDTO getCreditBill(UUID id);
    void closeCreditBill(UUID id);

    UUID getMasterBillId();
}
