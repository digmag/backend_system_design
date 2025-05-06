package ru.hits.metriccollector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metric")
public class MetricEntity {
    @Id
    private UUID id;

    @Column(name = "trace_id", nullable = false)
    private String traceId;

    @Column(name = "request_uri", nullable = false)
    private String requestUri;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "response_status", nullable = false)
    private int responseStatus;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
