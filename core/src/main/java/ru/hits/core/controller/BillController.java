package ru.hits.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillCreateDTO;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.core.service.interfaces.IBillService;

@RestController
@RequestMapping("/api/core/bill")
@RequiredArgsConstructor
public class BillController {
    private final IBillService billService;

    @PostMapping("/create")
    public BillResponseDTO create(@RequestBody BillCreateDTO billCreateDTO, Authentication authentication){
        return billService.create(billCreateDTO, authentication);
    }
}
