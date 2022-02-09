## Example gRPC

POC that requires the gRPC module which is not yet publicly available.

prepare artifacts

    mvn clean package

Deploy on Payara Server and make sure the HTTP/2 and HTTP Push are activate.

    ./asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.http2-push-enabled=true
    ./asadmin set configs.config.server-config.network-config.protocols.protocol.http-listener-1.http.http2-enabled=true

Before to run test client's

    Before to execute the test client's you must verify that an instance of the server is up and running. 
    This is because the test needs an available instance of the server to be able to deploy a generated war from arquillian

Run the test client's (located in the test folder) with the following command:

    From root folder grpc: mvn verify -Ppayara-server-remote -pl grpc-web
    
    you can also execute directly from the grpc-web folder as follows: mvn verify -Ppayara-server-remote

Execute individual tests with the following command:

    single thread execution use the folllowing command
    (under grpc-web folder): mvn verify -Ppayara-server-remote -Dtest=TestGrpc test

    for concurrent execution use the following command
    (under grpc-web folder): mvn verify -Ppayara-server-remote -Dtest=TestGrpcConcurrent test
