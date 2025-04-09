package ru.hits.sso.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "apps")
@AllArgsConstructor
@NoArgsConstructor
public class AppsEntity {
    @Id
    private UUID id;
    @Column(name = "service_id")
    private String serviceId;
    @Column(name = "user_id")
    private UUID userId;
}
