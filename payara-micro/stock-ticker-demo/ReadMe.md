# Stock Ticker Demo

This project demonstrates the use of the Payara Clustered CDI Event Bus to send data from Payara Instance to another. 
The project simulates a Stock Ticker which publishes the price of the Payara Stock every second.
This price is then broadcast onto the Clustered CDI Event Bus where it is picked up by another Payara instance
and streamed to the browser via web sockets to update a graph in real time.

The project consists of two Modules

## StockTicker
This is a Singleton EJB Timer bean which fires a Stock Object with a random price onto the Clustered CDI Event Bus every second.

## StockWeb
This is a web application which graphs in real time the value of the Payara Stock on an HTML 5 animated chart. 
The application consists of a WebSocket Endpoint and an Application Scoped CDI Bean which observes the Clustered CDI Events and 
updates the browser via Web Sockets whenever a Strock object is received.

## To Run
Build both applications using Maven. Then run two individual payara micro instances
```shell
java -jar payara-micro.jar --autobindhttp --deploy StockTicker-1.0-SNAPSHOT.jar
java -jar payara-micro.jar --autobindhttp --deploy StockTicker-1.0-SNAPSHOT.jar
```

Once running browse to;
http://127.0.0.1:8081/StockWeb-1.0-SNAPSHOT 
You should see an updating graph.
