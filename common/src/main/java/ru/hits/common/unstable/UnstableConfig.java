package ru.hits.common.unstable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.hits.common.security.exception.InternalServerError;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@Aspect
@ConditionalOnProperty(name = "unstable.enabled", havingValue = "true", matchIfMissing = false)
public class UnstableConfig {
    private final Random random = new Random();
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void anyRestController() {}
    @Around("anyRestController()")
    public Object maybeFail(ProceedingJoinPoint joinPoint) throws Throwable {
        int chance = getFailureChance();

        if (random.nextInt(100) < chance) {
            throw new InternalServerError("Вы нашли нестабильный сервис. у вас 500");
        }

        return joinPoint.proceed();
    }

    private int getFailureChance() {
        int minute = LocalDateTime.now().getMinute();
        return (minute % 2 == 0) ? 90 : 50;
    }
}
