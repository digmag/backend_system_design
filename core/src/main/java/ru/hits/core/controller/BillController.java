package ru.hits.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.TransactionCreateDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.core.service.interfaces.IBillService;
import ru.hits.core.service.interfaces.IIntegrationBillService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/bill")
@RequiredArgsConstructor
public class BillController {
    private final IBillService billService;
    private final IIntegrationBillService iBillService;

    @PostMapping("/create")
    public BillResponseDTO create(@RequestBody BillCreateDTO billCreateDTO, Authentication authentication, @RequestHeader(required = false) UUID ik){
        return billService.create(billCreateDTO, authentication, ik);
    }

    @PostMapping("/{id}/topup")
    public TransactionResponseDTO topUp(@PathVariable UUID id,
                                        @RequestBody TransactionCreateDTO transactionCreateDTO,
                                        Authentication authentication,
                                        @RequestHeader(required = false) UUID ik){
        return billService.topUp(id, transactionCreateDTO, authentication, ik);
    }

    @PostMapping("/{id}/topdown")
    public TransactionResponseDTO topdown(@PathVariable UUID id,
                                        @RequestBody TransactionCreateDTO transactionCreateDTO,
                                        Authentication authentication,
                                          @RequestHeader(required = false) UUID ik){
        return billService.topDown(id, transactionCreateDTO, authentication, ik);
    }

    @GetMapping("/my")
    public List<BillResponseDTO> getMyBills(Authentication authentication){
        return billService.getMyBills(authentication);
    }

    @PostMapping("/transaction/{from}/{to}")
    public String transaction(@PathVariable UUID from,
                                          @PathVariable UUID to,
                                          @RequestBody TransactionCreateDTO transactionCreateDTO,
                                          Authentication authentication,
                              @RequestHeader(required = false) UUID ik){
        return billService.transaction(from,to, transactionCreateDTO, authentication, ik);
    }

    @PatchMapping("/{id}/close")
    public void close(@PathVariable UUID id, Authentication authentication, @RequestHeader(required = false) UUID ik){
        billService.closeBill(id, authentication, ik);
    }

    @GetMapping("/transactions/{billId}")
    public List<TransactionResponseDTO> getBillsTransactions(@PathVariable UUID billId){
        return iBillService.getBillsTransactions(billId);
    }

    @PutMapping("/credit/{id}/close")
    public void closeBillInOneTap(@PathVariable UUID id, @RequestHeader(required = false) UUID ik){
        billService.closeBillInOneTap(id, ik);
    }
}
