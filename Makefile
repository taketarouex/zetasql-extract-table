grpc/zetasql-extract-table.pb.go:
	protoc -I ./grpc/ --go_out=plugins=grpc:. --go_opt=module=github.com/tktkc72/zetasql-extract-tables grpc/zetasql-extract-table.proto


