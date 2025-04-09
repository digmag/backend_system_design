package ru.hits.sso.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.sso.data.Apps;
import ru.hits.sso.data.AppsCreate;
import ru.hits.sso.data.Token;
import ru.hits.sso.service.SSOAppsService;
import ru.hits.sso.service.SSOLoginService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sso")
public class SSOController {
    private final SSOLoginService ssoLoginService;
    private final SSOAppsService ssoAppsService;

    @PostMapping("/login")
    public Token login(@RequestBody UserLoginDTO loginDTO){
        return ssoLoginService.login(loginDTO);
    }

    @GetMapping("/apps")
    public List<Apps> getMyApps(@RequestHeader(name = "Authorization") String authorizationHeader){
        return ssoAppsService.getMyApps(authorizationHeader);
    }

    @PostMapping("/authowire")
    public void authowire(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody AppsCreate appsCreate){
        ssoAppsService.authowire(authorizationHeader, appsCreate);
    }
}
