package ru.hits.core.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.TransactionCreateDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;

import java.util.List;
import java.util.UUID;

@Service
public interface IBillService {
    BillResponseDTO create(BillCreateDTO billCreateDTO, Authentication authentication);
    BillResponseDTO getBillInfo(UUID id, Authentication authentication);
    List<TransactionResponseDTO> getBillTransactions(UUID billId, Authentication authentication);
    void closeBill(UUID id, Authentication authentication);

    TransactionResponseDTO topUp(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication);
    TransactionResponseDTO topDown(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication);
    List<BillResponseDTO> getMyBills(Authentication authentication);
    TransactionResponseDTO transaction(UUID from,
                                       UUID to,
                                       TransactionCreateDTO transactionCreateDTO,
                                       Authentication authentication);
}
