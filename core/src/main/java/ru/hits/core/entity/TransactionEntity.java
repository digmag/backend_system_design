package ru.hits.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "bill_from", referencedColumnName = "id")
    private BillEntity billFrom;
    @ManyToOne
    @JoinColumn(name = "bill_to", referencedColumnName = "id")
    private BillEntity billTo;
    private Double amount;
}
