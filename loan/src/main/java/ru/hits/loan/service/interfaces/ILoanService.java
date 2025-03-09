package ru.hits.loan.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

@Service
public interface ILoanService {
    LoanResponseDTO create(LoanCreateDTO loanCreateDTO);
    void delete(UUID id);
    LoanResponseDTO getOne(UUID id);
    List<LoanResponseDTO> getAll();
}
