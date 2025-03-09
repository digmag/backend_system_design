package ru.hits.loan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.CreditBillCreateDTO;
import ru.hits.loan.service.interfaces.IDealService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/loan")
public class LoanController {
    private final IDealService dealService;

    @PostMapping("/bill")
    public BillResponseDTO createBill(@RequestBody CreditBillCreateDTO billCreateDTO, Authentication authentication){
        return dealService.createCreditBill(billCreateDTO, authentication);
    }
}
