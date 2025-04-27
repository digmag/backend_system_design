package ru.hits.metriccollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.metriccollector.entity.MetricEntity;

import java.util.UUID;

@Repository
public interface MetricRepository extends JpaRepository<MetricEntity, UUID> {

}
