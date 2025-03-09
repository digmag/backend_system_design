package ru.hits.common.dtos.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDTO {
    private UUID id;
    private UUID userId;
    private Double amount;
    private Type type;
    private Status status;
    private String name;
}
