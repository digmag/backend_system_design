package ru.hits.loan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.loan.LoanCreateDTO;
import ru.hits.common.dtos.loan.LoanResponseDTO;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.loan.entity.LoanEntity;
import ru.hits.loan.repository.LoanRepository;
import ru.hits.loan.service.interfaces.ILoanService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {
    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public LoanResponseDTO create(LoanCreateDTO loanCreateDTO, UUID ik) {
        LoanEntity loan = new LoanEntity(
                ik,
                loanCreateDTO.getLoanName(),
                loanCreateDTO.getPercent(),
                true
        );
        var prev = loanRepository.findByIsActive(true).orElse(null);
        if(prev != null){
            prev.setIsActive(false);
            loanRepository.save(prev);
        }
        loanRepository.save(loan);
        return new LoanResponseDTO(
                loan.getId(),
                loan.getName(),
                loan.getPercent(),
                loan.getIsActive()
        );
    }

    @Override
    public void delete(UUID id, UUID ik) {
        loanRepository.deleteById(id);
    }

    @Override
    public LoanResponseDTO getOne(UUID id) {
        var loan = loanRepository.findById(id).orElse(null);
        if(loan == null){
            throw new NotFoundException("Не найдено");
        }
        return new LoanResponseDTO(
                loan.getId(),
                loan.getName(),
                loan.getPercent(),
                loan.getIsActive()
        );
    }

    @Override
    public List<LoanResponseDTO> getAll() {
        return loanRepository.findAll().stream().map(loanEntity -> new LoanResponseDTO(
                loanEntity.getId(),
                loanEntity.getName(),
                loanEntity.getPercent(),
                loanEntity.getIsActive()
                )).toList();
    }
}
