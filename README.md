# zetasql-extract-table-server

extracts table from sql using [ZetaSQL](https://github.com/google/zetasql).
It provides a grpc interface.

## Build

`./gradlew jibDockerBuild`

## Run

`docker-compose up`

## Usage

Install [grpcurl](https://github.com/fullstorydev/grpcurl)

``` bash
$ grpcurl -plaintext -d '{ "statement": "select * from test1"}' localhost:6565 extracttable.
ExtractTableNamesFromStatement/ExtractTableNames
```

## Requirements

- gradle

## Test

`./gradlew test`

## Update proto

`./gradlew generateProto`
