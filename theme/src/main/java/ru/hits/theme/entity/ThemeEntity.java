package ru.hits.theme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.theme.ThemeEnum;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "theme")
public class ThemeEntity {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    private ThemeEnum theme;
}
