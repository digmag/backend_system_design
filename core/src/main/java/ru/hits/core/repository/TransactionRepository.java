package ru.hits.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.core.entity.BillEntity;
import ru.hits.core.entity.TransactionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByBillFrom(BillEntity billFrom);
}
