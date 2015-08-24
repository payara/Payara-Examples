# Clustered CDI Events Example

This Payara Micro Example shows you how to use Payara Micro's clustered CDI event bus
to fire CDI events from one Payara Micro cluster member to another.

This example consists of a number of sub projects

## event-sender
This war file shows you how to send events from a Servlet via CDI

## event-receiver
This war file shows how to receive clustered CDI events

## eventdata
A common library for the POJO event object sent between the applications

## ClusteredDeployExample
This application shows how to use Payara Micro's clustered administration command
capabilities to deploy the war files onto a Payara Micro Cluster.

##Running the Example

First ensure your current working directory is the ClusteredDeploymentExample root directory
Run a couple of empty payara micro instances each in their own terminal window. 
You can even run the empty payara micro instances on separate machines.

You will need to specify the full path to your payara micro jar file.
```shell
java -jar payara-micro.jar --autoBindHttp
java -jar payara-micro.jar --autoBindHttp
```
Then in the ClusteredDeploymentExample window run the deployment example to deploy
the war files across the payara micro cluster

```shell
mvn compile exec:exec
```

### Send an event
Use curl to send an event from the event-sender application
```shell
curl http://127.0.0.1:8081/event-sender-1.0-SNAPSHOT/SendEventServlet?message=curl-message
```

Using a browser navigate to the second web application to view the received events
http://127.0.0.1:8080//event-receiver-1.0-SNAPSHOT/EventsServlet 
