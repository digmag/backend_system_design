package ru.hits.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.theme.ThemeResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.theme.service.ThemeServicee;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/theme")
public class ThemeController {
    private final ThemeServicee themeServicee;

    @GetMapping
    public ThemeResponseDTO getTheme(Authentication authentication){
        return themeServicee.getTheme(authentication);
    }

    @PostMapping
    public ThemeResponseDTO setTheme(Authentication authentication, @RequestBody ThemeResponseDTO themeResponseDTO){
       return themeServicee.setTheme(authentication, themeResponseDTO);
    }

}
