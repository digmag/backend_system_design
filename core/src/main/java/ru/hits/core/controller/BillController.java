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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/bill")
@RequiredArgsConstructor
public class BillController {
    private final IBillService billService;

    @PostMapping("/create")
    public BillResponseDTO create(@RequestBody BillCreateDTO billCreateDTO, Authentication authentication){
        return billService.create(billCreateDTO, authentication);
    }

    @PostMapping("/{id}/topup")
    public TransactionResponseDTO topUp(@PathVariable UUID id,
                                        @RequestBody TransactionCreateDTO transactionCreateDTO,
                                        Authentication authentication){
        return billService.topUp(id, transactionCreateDTO, authentication);
    }

    @PostMapping("/{id}/topdown")
    public TransactionResponseDTO topdown(@PathVariable UUID id,
                                        @RequestBody TransactionCreateDTO transactionCreateDTO,
                                        Authentication authentication){
        return billService.topDown(id, transactionCreateDTO, authentication);
    }

    @GetMapping("/my")
    public List<BillResponseDTO> getMyBills(Authentication authentication){
        return billService.getMyBills(authentication);
    }

    @PostMapping("/transaction/{from}/{to}")
    public TransactionResponseDTO transaction(@PathVariable UUID from,
                                          @PathVariable UUID to,
                                          @RequestBody TransactionCreateDTO transactionCreateDTO,
                                          Authentication authentication){
        return billService.transaction(from,to, transactionCreateDTO, authentication);
    }

    @PatchMapping("/{id}/close")
    public void close(@PathVariable UUID id, Authentication authentication){
        billService.closeBill(id, authentication);
    }
}
