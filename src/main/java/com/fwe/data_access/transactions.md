Transactions play an important role in software, ensuring that data are never left in an inconsistent state. 
Transactions fullfil ACID:

_Atomic_ - Transactions are made up of one or more activites bundled together as a single unit of work. Atomicity ensures that all the operations in the transactions happen or that none of them happen. If all activites succeed, the transaction is a success.
In any of the activities fails, the entire transaction fails and is rolled back.

_Consistent_ - Once a transaction ends (whether sucessful or not), the system is left in a state consistent wiht the business that it models.

_Isolated_ - Transactions should allow multiple users to work with the same data, without each user's work getting tangled up with the others. Therefore, transactions should be isolated from each other, preventing concurrent reads and writes to the same data from occuring. (Note that isolation typically involves locking rows and/or tables in a database)

_Durable_ - Once the transaction has completed, the results of the transaction should be made permant so that they'll survive any sort of system crash.
This typically involves storing the results in a database or some other form of persistent storage.

Spring
Spring supports programmatic (for complete control) as well as declarative support 
for transactions. 

**Propagation*
Defines the boundaries of the transaction with respect to the client and the method being called.

**Isolation Levels**
In a typical application, multiple transactions run concurrently, oftern working with the same
data to get their jobs done. Concurrency, while necessary, can lead to the following problems:
- Dirty reads occur when one transaction reads data that has been written but not yet committed by another transaction. If the changes are later rolled back, the data obtained by the first transaction will be invalid.
- Lost updates occur when two transaction modify the same data, but only one transaction will be committed
- Nonrepeatable reads happen when a transaction performs the same query two or more times and each time the data is different. This
usually due to another concurrent transaction updating the data between the queries.
- Phantom reads are similar to nonrepeatable reads. These occur when a transaction (T1) reads several 


ISOLATION_READ_UNCOMMITED: Allow to read changes that haven't been committed. May results in dirty reads, phantom reads, and non repeatable reads.

A - write x = x+1 - rollback

B -  - read x -

-> B has x + 1 instead of x

ISOLATION_READ_COMMITTED: Allows reads from concurrent transactions that have been committed, dirty reads are prevented.

A - read x - - read x

B - - write x =  x + 1 - commit

-> First x and then x + 1

REPEATABLE_READ: Multiple reads of the same field will yield the same results, unless changed by the transaction itself. Dirty reads and nonrepeatable reads are prevented

A - read (where x > 10 and x < 20) - - - where (x > 10 and x < 20)
B - - write (x = 15) - commit

-> Result of A different in second fetch

SERIALIZABLE: Full ACID-compliant, but impact on concurrenty (often full table look)


![]()
