package ru.hits.common.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationResponse {
    private UUID id;
    private Status status;
    private String email;
}
