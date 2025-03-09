package ru.hits.common.dtos.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditBillCreateDTO {
    private String name;
    private Double amount;
    private UUID linkedBill;
    private LocalDate to;
}
