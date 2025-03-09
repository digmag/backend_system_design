package ru.hits.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.loan.entity.DealEntity;

import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, UUID> {
}
