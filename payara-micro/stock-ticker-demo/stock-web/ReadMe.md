# Stock Web

This is a web application that uses JavaEE websockets and the Payara CDI Clustered Event Bus to push data in real time to the browser and update an HTML 5 graph.

The application received Stock Events from the CDI Event Bus and sends them via WebSockets.
The Browser parses the JSON Stock object
The JSON data is used to update the data in a HighCharts chart.

To run the application on Payara Micro use;

```shell
java -jar payara-micro.jar --autobindhttp --deploy StockWeb-1.0-SNAPSHOT.war
```

Once deployed navigate to http://127.0.0.1:8081/StockWeb-1.0-SNAPSHOT

The actual URL will be displayed after the boot of Payara Micro for example;
```shell
Payara Micro URLs
http://MintyFresh:8081/StockWeb-1.0-SNAPSHOT

```
