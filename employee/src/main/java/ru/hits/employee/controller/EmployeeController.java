package ru.hits.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserRegistrationResponse;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.employee.feignClient.BillClient;
import ru.hits.employee.feignClient.UserClient;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/employee")
public class EmployeeController {
    @Autowired
    private final UserClient userClient;
    @Autowired
    private final BillClient billClient;
    @PostMapping("/client/registration")
    public UserRegistrationResponse registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return userClient.registration(userRegistrationDTO);
    }

    @PostMapping("/client/login")
    public TokensPair login(@RequestBody UserLoginDTO userLoginDTO){
        return userClient.login(userLoginDTO);
    }

    @GetMapping("/users")
    public List<UserResponseDTO> users(){
        return userClient.users();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getOne(@PathVariable UUID id){
        return userClient.getOne(id);
    }

    @GetMapping("/users/{userId}/bills")
    public List<BillResponseDTO> getUsersBills(@PathVariable UUID userId){
        return billClient.getUsersBills(userId);
    }

    @GetMapping("/bill/{billId}/transactions")
    public List<TransactionResponseDTO> getBillsTransactions(@PathVariable UUID billId){
        return billClient.getBillsTransactions(billId);
    }

}
