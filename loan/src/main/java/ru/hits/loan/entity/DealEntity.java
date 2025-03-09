package ru.hits.loan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deal")
public class DealEntity {
    @Id
    private UUID id;
    @JoinColumn(name = "loan", referencedColumnName = "id")
    @ManyToOne
    private LoanEntity loan;
    @Column(name = "bill_id")
    private UUID billId;
    private Double sum;
    private LocalDate during;
}
