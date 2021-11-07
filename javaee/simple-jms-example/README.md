# Simple JMS Example

This example contains 2 EJBs, an EJB timer bean called `Message Producer` to produce messages with the current date and time every 5 seconds and an MDB called `MessageConsumer` which just prints out the message received.

The queue that is used is `jms/simpleQ` and is configured in `MessageConsumer.java` by `@JMSDestinationDefinition` annotation so there is no manual configuration of Payara needed.
