package uk.me.mattgill.grpc.server;

import static java.lang.String.format;

import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import uk.me.mattgill.grpc.MyRequest;
import uk.me.mattgill.grpc.MyResponse;
import uk.me.mattgill.grpc.MyServiceGrpc.MyServiceImplBase;

public class MyService extends MyServiceImplBase {

    private static final Logger LOGGER = Logger.getLogger(MyService.class.getName());

    protected int getCount() {
        return 1;
    }

    @Override
    public void notify(MyRequest request, StreamObserver<MyResponse> responseObserver) {

        // Print the received message
        final String message = request.getMessage();
        LOGGER.info(format("Request received: \"%s\".", message));

        // Get the number of requests handled
        final int count = getCount();

        try {
            // Send ack
            responseObserver.onNext(createResponse("ACK: \"%s\".", message));
            Thread.sleep(800);

            // Return request count
            responseObserver.onNext(createResponse("Requests received: %d.", count));
            Thread.sleep(800);

            // Send final message part
            responseObserver.onNext(createResponse("Terminating response stream..."));
            Thread.sleep(800);

            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    private static final MyResponse createResponse(String message, Object... args) {
        return MyResponse.newBuilder() //
                .setMessage(format(message, args)) //
                .build();
    }

}
