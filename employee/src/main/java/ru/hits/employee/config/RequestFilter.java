package ru.hits.employee.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hits.common.security.JwtUserData;
import ru.hits.employee.config.factory.RequestFactory;
import ru.hits.employee.feignClient.UserClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.hits.common.security.SecurityConst.HEADER_JWT;
import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;

@RequiredArgsConstructor
@Component
public class RequestFilter implements Filter{
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final RequestFactory requestFactory;
    private final UserClient userClient;
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String httpPath = httpRequest.getServletPath();
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if(!httpPath.contains("/api/employee/client/login")) {
            var jwt = httpRequest.getHeader(HEADER_JWT);
            if (jwt == null) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            JwtUserData userData;
            try {
                var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
                var data = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt.replace(HEADER_PREFIX, ""));
                var idStr = String.valueOf(data.getBody().get("id"));
                userData = new JwtUserData(
                        idStr == null ? null : UUID.fromString(idStr)
                );
            } catch (JwtException e) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            try {
                requestFactory.create(httpPath, userData, httpResponse);
            } catch (IOException e) {
                System.out.println(httpPath + " " + userData.getId().toString());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
