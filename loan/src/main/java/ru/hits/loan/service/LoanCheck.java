package ru.hits.loan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.loan.repository.LoanRepository;
import ru.hits.loan.service.interfaces.ILoanCheck;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoanCheck implements ILoanCheck {
    private final LoanRepository loanRepository;
    @Override
    public Boolean isExists(UUID id) {
        return loanRepository.existsById(id);
    }
}
