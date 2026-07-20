package com.umesh.delivery_service.domain.retry.scheduler;

import com.umesh.delivery_service.domain.retry.service.RetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private final RetryService retryService;

    @Scheduled(fixedDelay = 5000)
    public void scan() {

        retryService.publishReadyRetries();

    }

}