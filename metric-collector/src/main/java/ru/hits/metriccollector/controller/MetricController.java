package ru.hits.metriccollector.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.metric.MetricCreateDto;
import ru.hits.metriccollector.entity.MetricEntity;
import ru.hits.metriccollector.service.MetricCollectorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/metric")
public class MetricController {
    private final MetricCollectorService metricCollectorService;

    // Добавление новой метрики
    @PostMapping
    public ResponseEntity<MetricEntity> createMetric(@RequestBody @Valid MetricCreateDto metricCreateDto) {
        metricCollectorService.saveMetric(metricCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Получение всех метрик
    @GetMapping
    public ResponseEntity<List<MetricEntity>> getAllMetrics() {
        List<MetricEntity> metrics = metricCollectorService.getAllMetrics();
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

}
