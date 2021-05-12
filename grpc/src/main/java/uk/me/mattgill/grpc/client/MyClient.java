package uk.me.mattgill.grpc.client;

import static java.lang.String.format;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import uk.me.mattgill.grpc.MyRequest;
import uk.me.mattgill.grpc.MyResponse;
import uk.me.mattgill.grpc.MyServiceGrpc;
import uk.me.mattgill.grpc.MyServiceGrpc.MyServiceStub;

public class MyClient {

    private static final Logger LOGGER = Logger.getLogger(MyClient.class.getName());

    private final MyServiceStub stub;
    private CountDownLatch latch;

    public MyClient(Channel channel) {
        this.stub = MyServiceGrpc.newStub(channel);
    }

    public synchronized void message() throws InterruptedException {
        latch = new CountDownLatch(1);
        stub.notify(createRequest("Hello World"), new MyResponseObserver());
        latch.await(20, TimeUnit.SECONDS);
    }

    private final class MyResponseObserver implements StreamObserver<MyResponse> {

        @Override
        public void onNext(MyResponse value) {
            LOGGER.info(format("Response received: \"%s\".", value.getMessage()));
        }

        @Override
        public void onError(Throwable t) {
            LOGGER.severe(format("Error received: \"%s\".", t.getMessage()));
            latch.countDown();
        }

        @Override
        public void onCompleted() {
            latch.countDown();
        }

    }

    private static final MyRequest createRequest(String message) {
        return MyRequest.newBuilder() //
                .setMessage(message) //
                .build();
    }

}
