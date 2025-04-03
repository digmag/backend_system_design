package ru.hits.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.theme.service.ThemeService;

@RestController
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    //тут писать эндпоинты
}
