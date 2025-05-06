package ru.hits.metriccollector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.metric.MetricCreateDto;
import ru.hits.metriccollector.entity.MetricEntity;
import ru.hits.metriccollector.repository.MetricRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetricCollectorService {
    private final MetricRepository metricRepository;

    public void saveMetric(MetricCreateDto metricCreateDto) {
        var metricEntity = new MetricEntity();
        metricEntity.setId(UUID.randomUUID());
        metricEntity.setTraceId(metricCreateDto.getTraceId());
        metricEntity.setRequestUri(metricCreateDto.getRequestUri());
        metricEntity.setMethod(metricCreateDto.getMethod());
        metricEntity.setResponseStatus(metricCreateDto.getResponseStatus());
        metricEntity.setStartTime(metricCreateDto.getStartTime());
        metricEntity.setEndTime(metricCreateDto.getEndTime());
        metricEntity.setCreatedAt(LocalDateTime.now());

        metricRepository.save(metricEntity);
    }

    public List<MetricEntity> getAllMetrics() {
        return metricRepository.findAll();
    }
}
