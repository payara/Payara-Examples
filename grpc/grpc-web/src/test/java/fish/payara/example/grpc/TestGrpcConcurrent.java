package fish.payara.example.grpc;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(Arquillian.class)
public class TestGrpcConcurrent {

    @Deployment
    public static WebArchive deployWar() {
        return TestGrpc.deployWar();
    }

    @Test
    @RunAsClient
    public void testConcurrentGrpc() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++) {
            String client = String.format("[Client %s] ", i);
            executor.execute(new GrpcClient(client));
            Thread.sleep(500);
        }
        executor.awaitTermination(10, TimeUnit.SECONDS);
        Assert.assertTrue(true);
    }

    private static class GrpcClient implements Runnable {

        private String client;

        public GrpcClient(String client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                TestGrpc.executeRouteGuideClient(client);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
