package ru.hits.core.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.loan.DealResponseDTO;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "loan-client", url = "${LOAN_SERVICE_URL:http://localhost:8080}")
public interface LoanClient {
    @GetMapping("/deal/{id}")
    DealResponseDTO getDeal(@PathVariable UUID id);
}
