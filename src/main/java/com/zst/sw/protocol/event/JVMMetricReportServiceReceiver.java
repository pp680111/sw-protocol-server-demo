package com.zst.sw.protocol.event;

import com.alibaba.fastjson2.JSON;
import io.grpc.stub.StreamObserver;
import org.apache.skywalking.apm.network.common.v3.Commands;
import org.apache.skywalking.apm.network.language.agent.v3.JVMMetricCollection;
import org.apache.skywalking.apm.network.language.agent.v3.JVMMetricReportServiceGrpc;

public class JVMMetricReportServiceReceiver extends JVMMetricReportServiceGrpc.JVMMetricReportServiceImplBase {
    @Override
    public void collect(JVMMetricCollection request, StreamObserver<Commands> responseObserver) {
        System.out.println(JSON.toJSONString(request));
    }
}
