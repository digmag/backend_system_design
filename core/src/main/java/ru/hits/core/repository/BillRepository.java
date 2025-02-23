package ru.hits.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.core.entity.BillEntity;

import java.util.UUID;

public interface BillRepository extends JpaRepository<BillEntity, UUID> {
}
