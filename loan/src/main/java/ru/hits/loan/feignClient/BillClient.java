package ru.hits.loan.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.*;

import java.util.UUID;

@FeignClient(name = "bill-client", url = "${BILL_SERVICE_URL:http://localhost:8083}")
public interface BillClient {
    @GetMapping("/integration/bill/exists/{billId}")
    Boolean isBillExists(@PathVariable UUID billId);

    @PostMapping("/integration/bill/credit/{userId}/create/{id}")
    BillResponseDTO createCreditBill(@RequestBody CreditBillCreateDTO billCreateDTO,
                                     @PathVariable(name = "id") UUID id,
                                     @PathVariable(name = "userId") UUID userId);

    @PostMapping("/integration/bill/transaction/{from}/{to}")
    TransactionResponseDTO createTransaction(@PathVariable UUID from,
                                             @PathVariable UUID to,
                                             @RequestBody TransactionCreateDTO transactionCreateDTO);

    @GetMapping("/integration/bill/credit/{id}")
    BillResponseDTO getBill(@PathVariable UUID id);

    @PutMapping("/integration/bill/credit/close/{id}")
    void closeCreditBill(@PathVariable UUID id);
}
