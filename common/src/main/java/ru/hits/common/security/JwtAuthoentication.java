package ru.hits.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthoentication extends AbstractAuthenticationToken {

    public JwtAuthoentication(JwtUserData jwtUserData){
        super(null);
        this.setDetails(jwtUserData);
        setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }
}
