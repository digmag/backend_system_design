package ru.hits.common.dtos.metric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricCreateDto {
    private String traceId;
    private String requestUri;
    private String method;
    private int responseStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
