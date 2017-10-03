# ESJC: elasticsearch-java-client

ESJC is a Java client for using ElasticSearch over the http API.

## Why?

ElasticSearch has an internal Java interface, so why have a client library?

1. The HTTP API is more stable than the native Java interface.
2. Using a native Java client can complicate the task of configuring ElasticSearch.

## Versions

The ESJC master branch tracks the latest stable version of ElasticSearch.

You can get versions of ESJC to match other versions of ElasticSearch in the branches of this repo, names `esversion/{number}`. 
E.g. here is ESJC for ElasticSearch version 2: https://github.com/winterstein/elasticsearch-java-client/tree/esversion/2

Because the http API is fairly stable, the same code usually works across a major version number of ElasticSearch.

## Alternatives

JEST: https://github.com/searchbox-io/Jest/tree/master/jest

Comparison: JEST has better coverage of features, whilst ESJC has better multi-thread and async handling, plus support for easier debugging of ES queries. JEST is available via Maven.

