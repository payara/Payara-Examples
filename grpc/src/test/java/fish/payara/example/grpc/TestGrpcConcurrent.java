package fish.payara.example.grpc;

import java.util.Random;

public class TestGrpcConcurrent {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String client = String.format("[Client %s] ", i);
            new Thread(new GrpcClient(client)).start();

            int sleepTime = random.nextInt(300);
            Thread.sleep(sleepTime);
        }

    }

    private static class GrpcClient implements Runnable {

        private String client;

        public GrpcClient(String client) {

            this.client = client;
        }

        @Override
        public void run() {
            try {
                TestGrpc.main(new String[]{client});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
