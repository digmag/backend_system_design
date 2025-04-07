package ru.hits.sso.configuration;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.sso.repository.entity.UserEntity;
import ru.hits.sso.repository.jpa.UserRepository;
import jakarta.servlet.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.hits.common.security.SecurityConst.*;
import static ru.hits.common.security.SecurityConst.HEADER_API_KEY;
@Component
@RequiredArgsConstructor
public class SSOAuthMiddleware implements Filter {
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final UserRepository userRepository;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if(!httpRequest.getServletPath().contains("/api/sso/login")) {
            var jwt = httpRequest.getHeader(HEADER_JWT);
            var api = httpRequest.getHeader(HEADER_API_KEY);
            if (jwt == null && api == null) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            if(jwt != null) {
                try {
                    var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
                    var body = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jwt.replace(HEADER_PREFIX, ""));
                    UserEntity user = userRepository.findById(UUID.fromString(String.valueOf(body.getBody().get("id"))))
                            .orElse(null);
                    if (user == null) {
                        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                        return;
                    }
                } catch (JwtException e) {
                    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
