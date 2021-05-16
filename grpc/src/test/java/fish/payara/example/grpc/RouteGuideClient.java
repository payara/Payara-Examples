package fish.payara.example.grpc;

import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.routeguide.*;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RouteGuideClient {

    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    private final RouteGuideGrpc.RouteGuideStub asyncStub;
    private final String clientPrefix;

    private final RouteGuideUtil routeGuideUtil;
    private final Random random = new Random();

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    public RouteGuideClient(Channel channel, String clientPrefix) {
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        asyncStub = RouteGuideGrpc.newStub(channel);
        routeGuideUtil = new RouteGuideUtil();
        this.clientPrefix = clientPrefix;
    }

    /**
     * Blocking unary call example.  Calls getFeature and prints the response.
     */
    public void getFeature(int lat, int lon) {
        LogHelper.info(clientPrefix+"*** GetFeature: lat={0} lon={1}", lat, lon);

        Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();

        Feature feature;
        try {
            feature = blockingStub.getFeature(request);

        } catch (StatusRuntimeException e) {
            LogHelper.warning(clientPrefix+"RPC failed: {0}", e.getStatus());

            return;
        }
        if (routeGuideUtil.exists(feature)) {
            LogHelper.info(clientPrefix+"Found feature called \"{0}\" at {1}, {2}",
                    feature.getName(),
                    routeGuideUtil.getLatitude(feature.getLocation()),
                    routeGuideUtil.getLongitude(feature.getLocation()));
        } else {
            LogHelper.info(clientPrefix+"Found no feature at {0}, {1}",
                    routeGuideUtil.getLatitude(feature.getLocation()),
                    routeGuideUtil.getLongitude(feature.getLocation()));
        }
    }

    /**
     * Blocking server-streaming example. Calls listFeatures with a rectangle of interest. Prints each
     * response feature as it arrives.
     */
    public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon) {
        LogHelper.info(clientPrefix+"*** ListFeatures: lowLat={0} lowLon={1} hiLat={2} hiLon={3}", lowLat, lowLon, hiLat,
                hiLon);

        Rectangle request =
                Rectangle.newBuilder()
                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features;
        try {
            features = blockingStub.listFeatures(request);
            for (int i = 1; features.hasNext(); i++) {
                Feature feature = features.next();
                LogHelper.info(clientPrefix+"Result #" + i + ": {0}", feature);

            }
        } catch (StatusRuntimeException e) {
            LogHelper.warning(clientPrefix+"RPC failed: {0}", e.getStatus());

        }
    }

    /**
     * Async client-streaming example. Sends {@code numPoints} randomly chosen points from {@code
     * features} with a variable delay in between. Prints the statistics when they are sent from the
     * server.
     */
    public void recordRoute(List<Feature> features, int numPoints) throws InterruptedException {
        LogHelper.info(clientPrefix+"*** RecordRoute");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
            @Override
            public void onNext(RouteSummary summary) {
                LogHelper.info(clientPrefix+"Finished trip with {0} points. Passed {1} features. "
                                + "Travelled {2} meters. It took {3} seconds.", summary.getPointCount(),
                        summary.getFeatureCount(), summary.getDistance(), summary.getElapsedTime());

            }

            @Override
            public void onError(Throwable t) {
                LogHelper.warning(clientPrefix+"RecordRoute Failed: {0}", Status.fromThrowable(t));

                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                LogHelper.info(clientPrefix+"Finished RecordRoute");
                finishLatch.countDown();
            }
        };

        StreamObserver<Point> requestObserver = asyncStub.recordRoute(responseObserver);
        try {
            // Send numPoints points randomly selected from the features list.
            for (int i = 0; i < numPoints; ++i) {
                int index = random.nextInt(features.size());
                Point point = features.get(index).getLocation();
                LogHelper.info(clientPrefix+"Visiting point {0}, {1}", routeGuideUtil.getLatitude(point),
                        routeGuideUtil.getLongitude(point));
                requestObserver.onNext(point);
                // Sleep for a bit before sending the next one.
                Thread.sleep(random.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                    return;
                }
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        // Receiving happens asynchronously
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            LogHelper.warning(clientPrefix+"recordRoute can not finish within 1 minutes");
        }
    }

    /**
     * Bi-directional example, which can only be asynchronous. Send some chat messages, and print any
     * chat messages that are sent from the server.
     */
    public CountDownLatch routeChat() {
        LogHelper.info(clientPrefix+"*** RouteChat");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RouteNote> requestObserver =
                asyncStub.routeChat(new StreamObserver<RouteNote>() {
                    @Override
                    public void onNext(RouteNote note) {
                        LogHelper.info(clientPrefix+"Got message \"{0}\" at {1}, {2}", note.getMessage(), note.getLocation()
                                .getLatitude(), note.getLocation().getLongitude());

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogHelper.warning(clientPrefix+"RouteChat Failed: {0}", Status.fromThrowable(t));

                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        LogHelper.info(clientPrefix+"Finished RouteChat");
                        finishLatch.countDown();
                    }
                });

        try {
            RouteNote[] requests =
                    {newNote("First message", 0, 0), newNote("Second message", 0, 10_000_000),
                            newNote("Third message", 10_000_000, 0), newNote("Fourth message", 10_000_000, 10_000_000)};

            for (RouteNote request : requests) {
                LogHelper.info(clientPrefix+"Sending message \"{0}\" at {1}, {2}", request.getMessage(), request.getLocation()
                        .getLatitude(), request.getLocation().getLongitude());
                requestObserver.onNext(request);
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        // return the latch while receiving happens asynchronously
        return finishLatch;
    }


    private RouteNote newNote(String message, int lat, int lon) {
        return RouteNote.newBuilder().setMessage(message)
                .setLocation(Point.newBuilder().setLatitude(lat).setLongitude(lon).build()).build();
    }
}
