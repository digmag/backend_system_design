package ru.hits.employee.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "loan-client", url = "${LOAN_SERVICE_URL:http://localhost:8080}")
public interface LoanClient {
    @GetMapping("/integration/loan/getone/{id}")
    LoanResponseDTO getOne(@PathVariable UUID id);

    @PostMapping("/integration/loan/create")
    LoanResponseDTO create(@RequestBody LoanCreateDTO loanCreateDTO);

    @DeleteMapping("/integration/loan/{id}")
    void delete(@PathVariable UUID id);

    @GetMapping("/integration/loan")
    List<LoanResponseDTO> allLoans();

    @GetMapping("/integration/loan/isexist/{id}")
    Boolean isExist(@PathVariable UUID id);
}
