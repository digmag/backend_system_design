package ru.hits.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.core.service.interfaces.IIntegrationBillService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integration/bill")
public class IntegrationController {
    private final IIntegrationBillService billService;

    @GetMapping("/users/{userId}")
    public List<BillResponseDTO> getUsersBills(@PathVariable UUID userId){
        return billService.getUsersBills(userId);
    }
    @GetMapping("/transactions/{billId}")
    public List<TransactionResponseDTO> getBillsTransactions(@PathVariable UUID billId){
        return billService.getBillsTransactions(billId);
    }

    @GetMapping("/exists/{id}")
    public Boolean isBillExists(@PathVariable UUID id){
        return billService.isBillExists(id);
    }

    @PostMapping("/credit/{userId}/create/{id}")
    BillResponseDTO createCreditBill(@RequestBody CreditBillCreateDTO billCreateDTO,
                                     @PathVariable(name = "id") UUID id,
                                     @PathVariable(name = "userId") UUID userId){
        return billService.createCreditBill(id, billCreateDTO, userId);
    }
}
