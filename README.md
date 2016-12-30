# elasticsearch-java-client

A Java client for using ElasticSearch over the http API

## Why?

ElasticSearch has an internal Java interface, so why have a client library?

1. The HTTP API is more stable than the native Java interface.
2. Using a native Java client can complicate the task of configuring ElasticSearch.
3. It is more robust across version changes.
