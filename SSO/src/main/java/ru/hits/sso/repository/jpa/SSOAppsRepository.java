package ru.hits.sso.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.sso.repository.entity.AppsEntity;

import java.util.List;
import java.util.UUID;

public interface SSOAppsRepository extends JpaRepository<AppsEntity, UUID> {
    List<AppsEntity> findAllByUserId(UUID id);
}
