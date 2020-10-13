package dev.tktkc72.sqlanalyzer;

import org.junit.Test;

import dev.tktkc72.sqlanalyzer.ZetasqlExtractTable.ExtractTableNamesRequest;
import dev.tktkc72.sqlanalyzer.ZetasqlExtractTable.ExtractTableNamesResponse;

import org.junit.Rule;
import static org.junit.Assert.*;

import io.grpc.testing.GrpcServerRule;

import dev.tktkc72.sqlanalyzer.App.ExtractTableNamesService;

public class AppTest {

    @Rule
    public GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

    @Test
    public void testExtractTableNamesService() {
        grpcServerRule.getServiceRegistry().addService(new ExtractTableNamesService());
        ExtractTableNamesGrpc.ExtractTableNamesBlockingStub blockingStub = ExtractTableNamesGrpc
                .newBlockingStub(grpcServerRule.getChannel());

        String sql = "with hoge as (select * from hogetest), bar as (select * from bartest) select * from hoge union all select * from bartest;";
        ExtractTableNamesResponse response = blockingStub
                .do_(ExtractTableNamesRequest.newBuilder().setStatement(sql).build());

        String[] expected = { "bartest", "hogetest" };
        assertArrayEquals(response.getTableNamesList().toArray(), expected);
    }
}
