# zetasql-extract-table-server

zetasql-extract-table-server extracts tables from sql using [ZetaSQL](https://github.com/google/zetasql).
This provides a grpc interface.

You can use it for data lineage.
For example, you can analyze a GCP View definition extracting the tables refereed in it.

## Build

`./gradlew jibDockerBuild`

## Run

`PORT=6565 docker-compose up`

## Usage

Install [grpcurl](https://github.com/fullstorydev/grpcurl)

``` bash
$ grpcurl -plaintext -d '{ "statement": "select * from test"}' localhost:6565 extracttable.
ExtractTableNamesFromStatement/ExtractTableNames

test
```

## Requirements

- gradle

## Test

`./gradlew test`

## Update proto

`./gradlew generateProto`

## Deploy

You can deploy it to Cloud Run.
You should configure memory limits more than 512MB.
