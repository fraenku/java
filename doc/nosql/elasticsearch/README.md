# ElasticSearch

## What is it?
ElasticSearch is an open-source, scalable search engine based on Lucene.
It is also considered as a document store, a document beeing a serialized json-file

## Where did it came from?
The base of ElasticSearch is Lucene, a library in Java (started around 1997),
great for full-text search, but a library and not foreseen for scaling.
Shay Banon started 2004 with a product called Compass on top of Lucene
to address the scalability issue. 2010 renaming to ElasticSearch.

## Who uses it?
eBay -> 800 millions listings in sub-seconds
Tinder -> For finding matches

## Use-Case
Indexing + Search
Logging
Collect metrics


## The main features
### Distributed and scalable
Node: An instance of Elasticsearch
Cluster: A collection of nudes
Index: A logical way to group data
Shard: A piece of an index, distributed among different nodes

### Can be used by any language through an REST API


