package ru.hits.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hits.common.dtos.bill.Status;
import ru.hits.common.dtos.bill.Type;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill")
public class BillEntity {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    private Integer amount;
    private Type type;
    private Status status;
    private String name;
}
