# Distributed processing with Transaction Locks

POC for distributed processing with Enabling Transaction Locks in Spring Data JPA

When there are multiple replica of an app processing at the same time, with the help of transaction locks we can
mitigate the issue of data inconsistency due to concurrent access by multiple threads or multiple replicas.

### What are Transaction Locks?

Transaction locks in Spring Data JPA are a mechanism used to ensure data consistency in multi-threaded environments
where multiple transactions may attempt to access and modify the same data concurrently. The relationship between a
transaction and a transactional lock is that a transactional lock is typically used within a transaction to prevent
concurrent access to a specific entity or set of entities. In a transactional environment, multiple threads can access
the same data simultaneously, which can lead to data inconsistencies or incorrect results.

To prevent this, Spring Data JPA offers transactional locks that allow developers to lock a specific entity or set of
entities during a transaction. When an entity is locked, it can only be accessed and modified by the transaction that
holds the lock, and other transactions are prevented from accessing or modifying the entity until the lock is released.