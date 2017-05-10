# JMS Client Example
(from Payara Micro 172 onwards)

This Payara Micro example shows how to send and receive JMS messages to ActiveMQ from Payara Micro.

The example consists of an MDB that receives messages from a Queue names TESTQ and a Timer Bean that
periodically sends messages to the same Queue.

For this example to run you must first install ActiveMQ and ensure it is running on standard ports.
You must also download the ActiveMQ rar file, in particular version activemq-rar-5.14.5. If you have a different download
version of ActiveMQ, modify the source code that refers to the rar to the same version as you have downloaded.

Once ActiveMQ is running create a queue called TESTQ.

To run the application on Payara Micro once built ensure that you deploy both the rar file and the ejb jar.

```shell
java -jar payara-micro.jar --autobindhttp --deploy activemq-rar-5.14.5.rar --deploy JMS-Client-Example-1.0-SNAPSHOT.jar
```

When the application is running you should see output like;

```shell
Got message ActiveMQTextMessage {commandId = 13, responseRequired = true, messageId = ID:MintyFresh-36284-1494434785461-3:1:2:1:1, originalDestination = null, originalTransactionId = null, producerId = ID:MintyFresh-36284-1494434785461-3:1:2:1, destination = queue://TESTQ, transactionId = null, expiration = 0, timestamp = 1494434795008, arrival = 0, brokerInTime = 1494434795008, brokerOutTime = 1494434795010, correlationId = null, replyTo = null, persistent = true, type = null, priority = 4, groupID = null, groupSequence = 0, targetConsumerId = null, compressed = false, userID = null, content = org.apache.activemq.util.ByteSequence@2fee6b02, marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, properties = null, readOnlyProperties = true, readOnlyBody = true, droppable = false, jmsXGroupFirstForConsumer = false, text = This is a test at Wed May 10 17:46:35 BST 2017}
```
