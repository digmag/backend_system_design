package ru.hits.loan.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;

import java.util.UUID;

@FeignClient(name = "bill-client", url = "${BILL_SERVICE_URL:http://localhost:8083}")
public interface BillClient {
    @GetMapping("/integration/bill/exists/{billId}")
    Boolean isBillExists(@PathVariable UUID billId);

    @PostMapping("/integration/bill/credit/{userId}/create/{id}")
    BillResponseDTO createCreditBill(@RequestBody CreditBillCreateDTO billCreateDTO,
                                     @PathVariable(name = "id") UUID id,
                                     @PathVariable(name = "userId") UUID userId);
}
