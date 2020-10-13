package dev.tktkc72.sqlanalyzer;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.stub.StreamObserver;

import java.util.stream.Collectors;
import java.util.List;

import com.google.zetasql.Analyzer;

import dev.tktkc72.sqlanalyzer.ExtractTableNamesGrpc.ExtractTableNamesImplBase;
import dev.tktkc72.sqlanalyzer.ZetasqlExtractTable.ExtractTableNamesRequest;
import dev.tktkc72.sqlanalyzer.ZetasqlExtractTable.ExtractTableNamesResponse;
import dev.tktkc72.sqlanalyzer.ZetasqlExtractTable.ExtractTableNamesResponse.Builder;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @GRpcService
    public static class ExtractTableNamesService extends ExtractTableNamesImplBase {
        @Override
        public void do_(ExtractTableNamesRequest request, StreamObserver<ExtractTableNamesResponse> responseObserver) {
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
