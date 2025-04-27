package ru.hits.loan.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import ru.hits.common.dtos.metric.MetricCreateDto;
import ru.hits.loan.feignClient.MetricClient;

import java.time.LocalDateTime;

@Aspect
@Component
public class MetricAspect {

    private final MetricClient metricClient;

    public MetricAspect(MetricClient metricClient) {
        this.metricClient = metricClient;
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void beforeRequest() {
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder
                .currentRequestAttributes()
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        if (request != null) {
            String traceId = request.getHeader("X-Trace-Id");
            String requestUri = request.getRequestURI();
            String method = request.getMethod();
            var startTime = LocalDateTime.now();

            sendMetric(traceId, requestUri, method, startTime, null, 0);

            RequestContextHolder.currentRequestAttributes().setAttribute("startTime", startTime, RequestAttributes.SCOPE_REQUEST);
        }
    }

    @After("restControllerMethods()")
    public void afterRequest() {
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder
                .currentRequestAttributes()
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        if (request != null) {
            String traceId = request.getHeader("X-Trace-Id");
            String requestUri = request.getRequestURI();
            String method = request.getMethod();
            int responseStatus = 200;
            var startTime = (LocalDateTime) RequestContextHolder.currentRequestAttributes()
                    .getAttribute("startTime", RequestAttributes.SCOPE_REQUEST);
            var endTime = LocalDateTime.now();

            sendMetric(traceId, requestUri, method, startTime, endTime, responseStatus);
        }
    }

    private void sendMetric(String traceId, String requestUri, String method, LocalDateTime startTime,
                            LocalDateTime endTime, int responseStatus) {
        MetricCreateDto metricCreateDto = new MetricCreateDto();
        metricCreateDto.setTraceId(traceId);
        metricCreateDto.setRequestUri(requestUri);
        metricCreateDto.setMethod(method);
        metricCreateDto.setStartTime(startTime);
        metricCreateDto.setEndTime(endTime);
        metricCreateDto.setResponseStatus(responseStatus);

        metricClient.createMetric(metricCreateDto);
    }
}

