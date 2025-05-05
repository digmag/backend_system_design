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

        String path = httpRequest.getServletPath();
        String ikHeader = httpRequest.getHeader("ik");
        UUID iKey = null;

        if (ikHeader != null && !ikHeader.isBlank()) {
            try {
                iKey = UUID.fromString(ikHeader);
                System.out.println("Получен ключ ik: " + iKey);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный формат UUID для ik: " + ikHeader);
                throw new ServletException("Некорректный UUID в заголовке 'ik'", e);
            }
        } else {
            System.out.println("Заголовок ik отсутствует или пуст");
        }

        System.out.println("Путь запроса: " + path);

        System.out.println(httpRequest.getServletPath());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
