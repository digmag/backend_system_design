package ru.hits.common.dtos.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private UUID id;
    private BillResponseDTO from;
    private BillResponseDTO to;
    private Double amount;
}
