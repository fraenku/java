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
