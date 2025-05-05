package ru.hits.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.bill.BillResponseDTO;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;
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
import ru.hits.employee.feignClient.LoanClient;
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
    @Autowired
    private final LoanClient loanClient;
    @PostMapping("/client/registration")
    public ResponseEntity<String> registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        try {
            userClient.registration(userRegistrationDTO);
            return  ResponseEntity.ok("Зарегистрировали");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(401).body("Не удалось зарегистрировать");
        }

    }

    @PostMapping("/client/login")
    public TokensPair login(@RequestParam("code") String token){
        return userClient.login(token);
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

    @PatchMapping("/users/block/{id}")
    public void  block(@PathVariable UUID id){
        userClient.block(id);
    }

    @PostMapping("/loan/create")
    public LoanResponseDTO create(@RequestBody LoanCreateDTO loanCreateDTO, @RequestHeader(required = false) UUID ik){
        return loanClient.create(loanCreateDTO, ik);
    }

    @GetMapping("/loan")
    public List<LoanResponseDTO> getAllLoan(){
        return loanClient.allLoans();
    }

    @GetMapping("/loan/{id}")
    public LoanResponseDTO getOneLoan(@PathVariable UUID id){
        return loanClient.getOne(id);
    }

    @DeleteMapping("/loan/{id}")
    public void delete(@PathVariable UUID id, @RequestHeader(required = false) UUID ik){
        loanClient.delete(id, ik);
    }

}
