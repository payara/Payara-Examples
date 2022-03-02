package fish.payara.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.routeguide.Feature;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(Arquillian.class)
public class TestGrpc {

    public static final String STUBS_IMPL = "fish.payara.grpc:grpc-stubs";

    private static List<String> listOfLibs;

    static {
        listOfLibs = new ArrayList<>();
        listOfLibs.add(STUBS_IMPL);
    }

    @Deployment
    public static WebArchive deployWar() {
        MavenResolverSystem resolver = Maven.resolver();
        File[] singleDependencies = resolver.loadPomFromFile("pom.xml").resolve(listOfLibs).withoutTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class).addPackage(FeatureRepository.class.getPackage())
                .addAsWebInfResource("glassfish-web.xml").addAsLibraries(singleDependencies)
                .addAsResource(TestGrpc.class.getResource("route_guide_db.json"), "fish/payara/example/grpc/route_guide_db.json");
        return war;
    }

    @Test
    @RunAsClient
    public void testGrpc() throws InterruptedException {
        executeRouteGuideClient(null);
        Assert.assertTrue(true);
    }

    public static void executeRouteGuideClient(String clientId) throws InterruptedException {
        String target = "localhost:8080";
        String clientIdPrefix = "[] ";

        if(clientId != null) {
            clientIdPrefix = clientId;
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
            RouteGuideClient client = new RouteGuideClient(channel, clientIdPrefix);
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
                LogHelper.warning(clientIdPrefix + "routeChat can not finish within 1 minutes");
            }

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

}
