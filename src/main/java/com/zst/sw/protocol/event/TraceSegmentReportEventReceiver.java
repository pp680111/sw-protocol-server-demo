package com.zst.sw.protocol.event;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.skywalking.apm.network.common.v3.Commands;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentCollection;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.apache.skywalking.apm.network.language.agent.v3.TraceSegmentReportServiceGrpc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class TraceSegmentReportEventReceiver extends TraceSegmentReportServiceGrpc.TraceSegmentReportServiceImplBase {
    Writer writer;
    public TraceSegmentReportEventReceiver() {
        try {
            File f = Paths.get("output").toFile();;
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(f));
        } catch (Exception e) {

        }
    }
    @Override
    public StreamObserver<SegmentObject> collect(StreamObserver<Commands> responseObserver) {


        return new StreamObserver<SegmentObject>() {
            @Override
            public void onNext(SegmentObject segmentObject) {
                try {
                    writer.append(JSON.toJSONString(segmentObject, JSONWriter.Feature.PrettyFormat));
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                Status status = Status.fromThrowable(throwable);
                if (Status.CANCELLED.getCode() == status.getCode()) {
                    return;
                }
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Commands.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void collectInSync(SegmentCollection request, StreamObserver<Commands> responseObserver) {
        super.collectInSync(request, responseObserver);
    }
}
