package ru.hits.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.loan.entity.LoanEntity;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanEntity, UUID> {
}
