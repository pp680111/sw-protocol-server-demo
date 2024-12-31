package com.zst.sw.protocol.event;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = Grpc.newServerBuilderForPort(50051, InsecureServerCredentials.create())
                .addService(new TraceSegmentReportEventReceiver())
//                .addService(new JVMMetricReportServiceReceiver())
//                .addService(new EventServiceReceiver())
                .build()
                .start();
        server.awaitTermination();
//        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 50051);
//        NettyServerBuilder nettyServerBuilder = NettyServerBuilder.forAddress(address);
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        nettyServerBuilder.executor(executor);
//        Server server = nettyServerBuilder.build();
//        server.start();
//
//        server.awaitTermination();
    }
}
