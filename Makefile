grpc/zetasql-extract-table.pb.go:
	protoc -I ./grpc/ --go_out=plugins=grpc:. --go_opt=module=github.com/tktkc72/zetasql-extract-table grpc/zetasql-extract-table.proto

grpc/com/tktkc72/zetasql_extract_table/ZetasqlExtractTable.java:
	protoc -I ./grpc --java_out=grpc grpc/zetasql-extract-table.proto
