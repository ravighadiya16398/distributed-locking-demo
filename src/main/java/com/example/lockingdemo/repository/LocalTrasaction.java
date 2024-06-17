package com.example.lockingdemo.repository;

import com.example.lockingdemo.entity.LockingTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocalTrasaction {
    private final LockTestrepository lockTestrepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<LockingTest> lockingTest(int page, String threadName, Integer counter) {
        Page<LockingTest> lockingTests;
        lockingTests = lockTestrepository.findAll(PageRequest.of(page, 100));
        if (lockingTests.isEmpty()) {
            return null;
        }
        for (LockingTest lockingTest : lockingTests.getContent()) {
            lockingTest.setThread(threadName);
            lockingTest.setReadStatus(true);
            lockTestrepository.save(lockingTest);
            counter++;
        }
        return lockingTests;
    }
}
