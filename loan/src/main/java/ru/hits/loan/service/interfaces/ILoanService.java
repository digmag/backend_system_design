package ru.hits.loan.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

@Service
public interface ILoanService {
    LoanResponseDTO create(LoanCreateDTO loanCreateDTO, UUID ik);
    void delete(UUID id, UUID ik);
    LoanResponseDTO getOne(UUID id);
    List<LoanResponseDTO> getAll();
}
