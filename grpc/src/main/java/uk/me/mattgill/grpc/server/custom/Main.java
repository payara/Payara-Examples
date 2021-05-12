package uk.me.mattgill.grpc.server.custom;

import java.io.IOException;

import uk.me.mattgill.grpc.Properties;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServer server = new MyServer(Properties.GRPC_PORT);
        server.start();
        server.blockUntilShutdown();
    }

}
