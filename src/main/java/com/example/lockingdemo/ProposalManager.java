package com.example.lockingdemo;

import com.example.lockingdemo.entity.LockingTest;
import com.example.lockingdemo.repository.LocalTrasaction;
import com.example.lockingdemo.repository.LockTestrepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProposalManager {

    private final TransactionTemplate transactionManager;
    private final LockTestrepository lockTestrepository;
    private final LocalTrasaction localTrasaction;


    @Value("${app.name}")
    private String appName;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void execute() {
        Thread.currentThread().setName(appName);
        String threadName = Thread.currentThread().getName();
        log.info("Thread {} started", threadName);

        Integer counter = 0;

        int page = 0;
        Page<LockingTest> lockingTests;
        do {
            log.info("procesing page : {}", page);
            lockingTests = localTrasaction.lockingTest(page++, threadName, counter);
            if (lockingTests == null) {
                break;
            }
        } while (lockingTests.getTotalPages() >= page);

        log.info("App: {} , Total rows processed: {}", appName, counter);
    }
}
