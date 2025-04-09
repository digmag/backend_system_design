package ru.hits.common.dtos.theme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeResponseDTO {
    private ThemeEnum theme;
}
