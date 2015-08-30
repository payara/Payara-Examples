# Autobind Cluster

This Payara Micro example shows you how to bootstrap a Payara Micro Server so that it can easily cluster with other Payara Micro
Servers on the same host and/or multiple hosts. The example uses the autobind feature to determine a free port to use to listen for HTTP requests.

This example also shows how you can add a cluster listener to the Payara Micro runtime so you can be notified when Payara Micro
Servers are started and exit.

Run this example a number of times using
```shell
mvn compile exec:exec
```
