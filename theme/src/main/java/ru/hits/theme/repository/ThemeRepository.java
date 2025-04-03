package ru.hits.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.theme.entity.ThemeEntity;

import java.util.UUID;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, UUID> {
}
