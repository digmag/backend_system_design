package ru.hits.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hits.common.dtos.bill.Type;
import ru.hits.core.entity.BillEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, UUID> {
    List<BillEntity> findAllByUserId(UUID userId);

    Optional<BillEntity> findByType(Type type);
}


