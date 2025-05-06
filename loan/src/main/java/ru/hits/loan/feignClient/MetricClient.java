package ru.hits.loan.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hits.common.dtos.metric.MetricCreateDto;

@FeignClient(name = "metric-client", url = "${METRIC_SERVICE_URL:http://localhost:8087}")
public interface MetricClient {
    @PostMapping("/api/metric")
    void createMetric(@RequestBody MetricCreateDto metricCreateDto);
}
