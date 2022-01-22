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
    public static final String PROTOBUF = "com.google.protobuf:protobuf-java";
    public static final String IO_GRPC_API = "io.grpc:grpc-api";
    public static final String IO_GRPC_STUB = "io.grpc:grpc-stub";
    public static final String PAYARA_GRPC = "fish.payara.extras:grpc";
    public static final String PROTOBUF_JAVA_UTIL = "com.google.protobuf:protobuf-java-util";

    private static List<String> listOfLibs;

    static {
        listOfLibs = new ArrayList<>();
        listOfLibs.add(STUBS_IMPL);
        listOfLibs.add(PROTOBUF);
        listOfLibs.add(IO_GRPC_API);
        listOfLibs.add(IO_GRPC_STUB);
        listOfLibs.add(PAYARA_GRPC);
        listOfLibs.add(PROTOBUF_JAVA_UTIL);
    }

    @Deployment
    public static WebArchive deployWar() {
        MavenResolverSystem resolver = Maven.resolver();
        File[] singleDependencies = resolver.loadPomFromFile("pom.xml").resolve(listOfLibs).withoutTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class).addPackage(FeatureRepository.class.getPackage())
                .addAsWebInfResource("glassfish-web.xml").addAsLibraries(singleDependencies)
                .addAsResource(TestGrpc.class.getResource("route_guide_db.json"), "fish/payara/example/grpc/route_guide_db.json");
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    @RunAsClient
    public void testGrpc() throws InterruptedException {
        String target = "localhost:8080";
        String clientPrefix = "[] ";

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
        Assert.assertTrue(true);
    }

}
