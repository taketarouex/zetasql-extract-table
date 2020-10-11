package zetasql.extract.table.server;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.stub.StreamObserver;

import java.util.stream.Collectors;
import java.util.List;

import com.google.zetasql.Analyzer;

import grpc.zetasql.extract.table.server.ExtractTableNamesFromStatementGrpc.ExtractTableNamesFromStatementImplBase;
import grpc.zetasql.extract.table.server.ZetasqlExtractTable.ExtractTableNamesRequest;
import grpc.zetasql.extract.table.server.ZetasqlExtractTable.ExtractTableNamesResponse;
import grpc.zetasql.extract.table.server.ZetasqlExtractTable.ExtractTableNamesResponse.Builder;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @GRpcService
    public static class ExtractTableNamesService extends ExtractTableNamesFromStatementImplBase {
        @Override
        public void extractTableNames(ExtractTableNamesRequest request,
                StreamObserver<ExtractTableNamesResponse> responseObserver) {
            Builder responseBuilder = ExtractTableNamesResponse.newBuilder();
            responseBuilder.addAllTableNames(extractTables(request.getStatement()));

            ExtractTableNamesResponse response = responseBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    private static List<String> extractTables(String sql) {
        return Analyzer.extractTableNamesFromStatement(sql).stream().map(list -> {
            return String.join(".", list);
        }).collect(Collectors.toList());
    }
}
