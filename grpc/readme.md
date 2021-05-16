## Example gRPC

Currently, a POC that requires the gRPC module which is not yet publicly available.

prepare artifacts

    mvn clean package

Deploy on Payara Server and make sure the HTTP/2 and HTTP Push are activate.

    ./asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.http2-push-enabled=true
    ./asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.http2-enabled=true


Run the client (located in the test folder) or use

    ./run-client.sh
