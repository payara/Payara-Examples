package fish.payara.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.routeguide.Feature;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestGrpc {

    /**
     * Issues several different requests and then exits.
     */
    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8080";

        String clientPrefix = "[] ";
        if (args.length > 0) {
            clientPrefix = args[0];
        }
        List<Feature> features;
        try {
            features = new RouteGuideJSONReader().readFeatures();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            RouteGuideClient client = new RouteGuideClient(channel, clientPrefix);
            // Looking for a valid feature
            client.getFeature(409146138, -746188906);

            // Feature missing.
            client.getFeature(0, 0);

            // Looking for features between 40, -75 and 42, -73.
            client.listFeatures(400000000, -750000000, 420000000, -730000000);

            // Record a few randomly selected points from the features file.
            client.recordRoute(features, 10);

            // Send and receive some notes.
            CountDownLatch finishLatch = client.routeChat();

            if (!finishLatch.await(1, TimeUnit.MINUTES)) {
                LogHelper.warning(clientPrefix + "routeChat can not finish within 1 minutes");
            }
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
