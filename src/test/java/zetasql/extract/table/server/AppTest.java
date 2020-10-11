package zetasql.extract.table.server;

import org.junit.Test;

import grpc.zetasql.extract.table.server.ZetasqlExtractTable.ExtractTableNamesRequest;
import grpc.zetasql.extract.table.server.ZetasqlExtractTable.ExtractTableNamesResponse;
import grpc.zetasql.extract.table.server.ExtractTableNamesFromStatementGrpc;

import org.junit.Rule;
import static org.junit.Assert.*;

import io.grpc.testing.GrpcServerRule;

import zetasql.extract.table.server.App.ExtractTableNamesService;

public class AppTest {

    @Rule
    public GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

    @Test
    public void testExtractTableNamesService() {
        grpcServerRule.getServiceRegistry().addService(new ExtractTableNamesService());
        ExtractTableNamesFromStatementGrpc.ExtractTableNamesFromStatementBlockingStub blockingStub = ExtractTableNamesFromStatementGrpc
                .newBlockingStub(grpcServerRule.getChannel());

        String sql = "with hoge as (select * from hogetest), bar as (select * from bartest) select * from hoge union all select * from bartest;";
        ExtractTableNamesResponse response = blockingStub
                .extractTableNames(ExtractTableNamesRequest.newBuilder().setStatement(sql).build());

        String[] expected = { "bartest", "hogetest" };
        assertArrayEquals(response.getTableNamesList().toArray(), expected);
    }
}
