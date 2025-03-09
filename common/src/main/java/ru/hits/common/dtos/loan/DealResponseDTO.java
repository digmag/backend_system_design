package ru.hits.common.dtos.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealResponseDTO {
    private UUID id;
    private UUID linkedBillId;
}
