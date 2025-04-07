package ru.hits.sso.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.sso.repository.entity.TokenEntity;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
}
