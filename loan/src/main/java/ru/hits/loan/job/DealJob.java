package ru.hits.loan.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.hits.loan.service.interfaces.IDealService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DealJob implements Job {
    private final IDealService dealService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        dealService.scheduleTransactions();
    }
}
