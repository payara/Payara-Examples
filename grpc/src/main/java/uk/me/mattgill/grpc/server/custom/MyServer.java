package uk.me.mattgill.grpc.server.custom;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import uk.me.mattgill.grpc.server.MyService;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class MyServer {

    private static final Logger LOGGER = Logger.getLogger(MyServer.class.getName());

    private final int port;

    private Server server;

    public MyServer(int port) {
        this.port = port;
    }

    protected void start() throws IOException {
        if (server == null) {
            server = ServerBuilder //
                    .forPort(port) //
                    .addService(new MyService()) //
                    .build() //
                    .start();
            LOGGER.info("Server started, listening on " + port);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Use stderr here since the logger may have been reset by its JVM shutdown
                // hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    MyServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }));
        }
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    protected void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}