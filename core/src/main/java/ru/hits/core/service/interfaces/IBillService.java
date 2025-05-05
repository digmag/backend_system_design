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
    BillResponseDTO create(BillCreateDTO billCreateDTO, Authentication authentication, UUID ik);
    void closeBill(UUID id, Authentication authentication, UUID ik);

    TransactionResponseDTO topUp(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication, UUID ik);
    TransactionResponseDTO topDown(UUID id, TransactionCreateDTO transactionCreateDTO, Authentication authentication, UUID ik);
    List<BillResponseDTO> getMyBills(Authentication authentication);
    String transaction(UUID from,
                                       UUID to,
                                       TransactionCreateDTO transactionCreateDTO,
                                       Authentication authentication,
                       UUID ik);
    List<TransactionResponseDTO> transactions(UUID id);
    void closeBillInOneTap(UUID id, UUID ik);
}
