package com.zst.sw.protocol.event;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.grpc.stub.StreamObserver;
import org.apache.skywalking.apm.network.common.v3.Commands;
import org.apache.skywalking.apm.network.event.v3.Event;
import org.apache.skywalking.apm.network.event.v3.EventServiceGrpc;

public class EventServiceReceiver extends EventServiceGrpc.EventServiceImplBase {
    @Override
    public StreamObserver<Event> collect(StreamObserver<Commands> responseObserver) {
        return new StreamObserver<Event>() {
            @Override
            public void onNext(Event event) {
                System.out.println(JSON.toJSONString(event, JSONWriter.Feature.PrettyFormat));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Commands.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }
}
