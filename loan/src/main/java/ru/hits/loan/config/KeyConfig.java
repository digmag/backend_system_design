package ru.hits.loan.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import ru.hits.loan.repository.DealRepository;
import ru.hits.loan.repository.LoanRepository;

import java.io.IOException;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class KeyConfig implements Filter {

    private final DealRepository dealRepository;
    private final LoanRepository loanRepository;

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String path = httpRequest.getServletPath();
        String ikHeader = httpRequest.getHeader("ik");
        UUID iKey = null;
        System.out.println(httpRequest.getMethod());

        if (ikHeader != null && !ikHeader.isBlank() && httpRequest.getMethod()=="POST") {
            try {
                iKey = UUID.fromString(ikHeader);
                if(path.contains("/api/loan/create")){
                    if(loanRepository.existsById(iKey)){
                        httpResponse.setStatus(200);
                        return;
                    }
                }
                if(path.contains("/integration/loan/create")){
                    if(dealRepository.existsById(iKey)){
                        httpResponse.setStatus(200);
                        return;
                    }
                }
                System.out.println("Получен ключ ik: " + iKey);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный формат UUID для ik: " + ikHeader);
                throw new ServletException("Некорректный UUID в заголовке 'ik'", e);
            }
        }

        System.out.println(httpRequest.getServletPath());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
