<h2>Partitioning (Partition-Key)</h2>
The primary-key is used to partition the data on the cassandra-ring. 
Example: Having records of customers the primary-key could be ((state), id), meaning that the data is clustered after the state.

<h2>Clustering columns</h2>
Columns after the partition-key, to order the data
