package ru.hits.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.bill.BillResponseDTO;
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
}
