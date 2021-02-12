# ActiveMQ JMS Client Example

This Payara Micro example shows how to send and receive JMS messages to ActiveMQ from Payara Micro.

The example consists of an MDB that receives messages from a Queue names TESTQ and a Timer Bean that
periodically sends messages to the same Queue.

## Requirements for running the example

For this example to run you must first install ActiveMQ and ensure it is running on standard ports.

Once ActiveMQ is running create a queue called TESTQ.

You don't have to download the ActiveMQ resource adapter because it will be downloaded automatically by Maven.

## Run the example application

You can run this example with:

```
mvn -Dactivemq.version=5.14.5 package payara-micro:start
```

If the version of your ActiveMQ server is not 5.14.5, specify a different version in the `activemq.version` property.

This command will:

* download and deploy the rar file with the version specified in the `activemq.version` property
* deploy the application
* set `--autobindhttp` and `--noCluster` to simplify and speed up startup

When the application is running you should see output like;

```shell
Got message ActiveMQTextMessage {commandId = 13, responseRequired = true, messageId = ID:MintyFresh-36284-1494434785461-3:1:2:1:1, originalDestination = null, originalTransactionId = null, producerId = ID:MintyFresh-36284-1494434785461-3:1:2:1, destination = queue://TESTQ, transactionId = null, expiration = 0, timestamp = 1494434795008, arrival = 0, brokerInTime = 1494434795008, brokerOutTime = 1494434795010, correlationId = null, replyTo = null, persistent = true, type = null, priority = 4, groupID = null, groupSequence = 0, targetConsumerId = null, compressed = false, userID = null, content = org.apache.activemq.util.ByteSequence@2fee6b02, marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, properties = null, readOnlyProperties = true, readOnlyBody = true, droppable = false, jmsXGroupFirstForConsumer = false, text = This is a test at Wed May 10 17:46:35 BST 2017}
```
