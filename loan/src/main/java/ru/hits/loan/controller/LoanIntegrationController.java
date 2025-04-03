package ru.hits.loan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.loan.DealResponseDTO;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;
import ru.hits.loan.service.interfaces.IDealService;
import ru.hits.loan.service.interfaces.ILoanCheck;
import ru.hits.loan.service.interfaces.ILoanService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/integration/loan")
public class LoanIntegrationController {

    private final ILoanService loanService;
    private final ILoanCheck loanCheck;
    private final IDealService dealService;

    @GetMapping("/getone/{id}")
    public LoanResponseDTO getOne(@PathVariable UUID id){
        return loanService.getOne(id);
    }

    @PostMapping("/create")
    public LoanResponseDTO create(@RequestBody LoanCreateDTO loanCreateDTO){
        return loanService.create(loanCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        loanService.delete(id);
    }

    @GetMapping
    public List<LoanResponseDTO> allLoans(){
        return loanService.getAll();
    }

    @GetMapping("/isexist/{id}")
    public Boolean isExist(@PathVariable UUID id){
        return loanCheck.isExists(id);
    }

    @GetMapping("/deal/{id}")
    public DealResponseDTO getDeal(@PathVariable UUID id){
        return dealService.getDeal(id);
    }

}
