package uk.me.mattgill.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import uk.me.mattgill.grpc.Properties;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final Channel channel = ManagedChannelBuilder //
                .forAddress(Properties.GRPC_HOST, Properties.GRPC_PORT) //
                .usePlaintext() // Required for insecure connections
                .build();
        new MyClient(channel).message();
    }

}