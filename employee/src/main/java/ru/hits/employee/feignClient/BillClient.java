package ru.hits.employee.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(name="bill-client",url = "${BILL_SERVICE_URL:http://localhost:8080}")
public interface BillClient {
    @GetMapping("/integration/bill/users/{userId}")
    List<BillResponseDTO> getUsersBills(@PathVariable UUID userId);
    @GetMapping("/integration/bill/transactions/{billId}")
    List<TransactionResponseDTO> getBillsTransactions(@PathVariable UUID billId);
    @GetMapping("/integration/bill/exists/{billId}")
    Boolean isBillExists(@PathVariable UUID billId);
}
