package ru.hits.common.dtos.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {
    private UUID id;
    private String name;
    private Double percent;
}
