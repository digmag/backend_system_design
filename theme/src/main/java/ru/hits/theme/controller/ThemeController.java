package ru.hits.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.theme.ThemeResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.theme.service.ThemeServicee;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/theme")
public class ThemeController {
    private final ThemeServicee themeServicee;

    @GetMapping
    public ThemeResponseDTO getTheme(Authentication authentication){
        return themeServicee.getTheme(authentication);
    }

    @PostMapping("/post")
    public ThemeResponseDTO setTheme(@RequestBody ThemeResponseDTO themeResponseDTO,
                                     @RequestParam(name = "token") String token,
                                     @RequestHeader(name = "ik") UUID ik){
       return themeServicee.setTheme(themeResponseDTO, token, ik);
    }

}
