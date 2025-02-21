package ru.hits.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.user.repository.entity.TokenEntity;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
}
