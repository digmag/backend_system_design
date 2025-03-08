package ru.hits.loan.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ILoanCheck {
    Boolean isExists(UUID id);
}
