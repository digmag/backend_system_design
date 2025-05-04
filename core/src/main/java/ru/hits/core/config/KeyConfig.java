package ru.hits.core.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.*;
import ru.hits.core.repository.BillRepository;
import ru.hits.core.repository.TransactionRepository;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class KeyConfig implements Filter {
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        UUID iKey = UUID.fromString(httpRequest.getHeader("ik"));
        System.out.println(httpRequest.getServletPath());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
