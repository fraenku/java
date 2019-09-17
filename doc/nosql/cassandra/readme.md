<h2>CAP-Theorem</h2>
Cassandra is optimised for Availability and Partition Tolearance. Consistency is hard in a distributed system, but can be tunned. 

<h2>Partitioning (Partition-Key)</h2>
The primary-key is used to partition the data on the cassandra-ring. 
Example: Having records of customers the primary-key could be ((state), id), meaning that the data is clustered after the state.

<h2>Clustering columns</h2>
Columns after the partition-key, to order the data

<h2>Node</h2>
Contains data, can handle 3000 - 5000 transaction per second per core and contain 2 - 4 Terrabytes of data.
SSD disk are recommended, but SAN discouraged.

<h2>Ring</h2>
If you can not handle the load, just add new nodes to the ring. There is no downtime. = Horzontal scalling with standard hardware. Doubling the number of nodes means doubling the capacity. 
Removing of nodes is also possible. 

<h2>Snitch</h2>
Snitch is used to determine each node's rack and datacenter and is configured in cassandra.yml

<h2>Replication</h2>
Determines the replication of data on nodes. A good value is 3. Is defined per keyspace. 
RF

<h2>Consistency</h2>
CL = 1, Acknowledged only per one node, fastest way
CL = Quorum =  51% of nodes (recommended), strongly consistent
CL = All, every node has to acknowledge. We are consistent, but turning partition tolerance and availability off. Slowest mode

<h2>Hinted Hand off</h2>
The cordinator keeps writes in a log file in a case a node is down for a limited time

<h2>When to use Cassandra</h2>
Updates: Cassandra is very good at writes, okay with reads. Updates and deletes are implemented as special cases of writes and that has consequences that are not immediately obvious.

The ideal Cassandra application has the following characteristics:

Writes exceed reads by a large margin.
Data is rarely updated and when updates are made they are idempotent.
Read Access is by a known primary key.
Data can be partitioned via a key that allows the database to be spread evenly across multiple nodes.
There is no need for joins or aggregates.

Some of my favorite examples of good use cases for Cassandra are:

Transaction logging: Purchases, test scores, movies watched and movie latest location.
Storing time series data (as long as you do your own aggregates).
Tracking pretty much anything including order status, packages etc.
Storing health tracker data.
Weather service history.
Internet of things status and event history.
Telematics: IOT for cars and trucks.
Email envelopes—not the contents.
