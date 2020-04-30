# OpenMQ JMS Client Example

This example shows how to send and receive JMS messages to OpenMQ from Payara Micro.

The example consists of an MDB that receives messages from a Queue and a Timer Bean that
periodically sends messages to the same queue.

## Requirements for running the example

For this example to run you must first install OpenMQ and ensure it is running on standard ports.
You must also copy the OpenMQ rar file from the OpenMQ installation. It's the file `imqjmsra.rar` in the `lib` directory.

## Run the example application

You can run this example with:

```
mvn -Dopenmq.rar.path=/path/to/imqjmsra.rar package payara-micro:start
```

This command will:

* deploy the rar file from the path specified by the `openmq.rar.path` property
* deploy the application
* apply the default system properties to cofigure the application
* set `--autobindhttp` and `--noCluster` to simplify and speed up startup

When the application is running you should see output like;

```shell
Got message 
Text:   This is a test at Thu Apr 30 10:26:15 CEST 2020
Class:                  com.sun.messaging.jmq.jmsclient.TextMessageImpl
getJMSMessageID():      ID:61-127.0.1.1(f5:a1:e8:8e:77:b2)-59164-1588235175006
getJMSTimestamp():      1588235175006
getJMSCorrelationID():  null
JMSReplyTo:             null
JMSDestination:         TESTQ
getJMSDeliveryMode():   PERSISTENT
getJMSRedelivered():    false
getJMSType():           null
getJMSExpiration():     0
getJMSDeliveryTime():   0
getJMSPriority():       4
Properties:             {JMSXDeliveryCount=1}
```

## Configure the application

The application is configured using MicroProfile Config properties. The default values are specified in the file [META-INF/microprofile-config.properties](src/main/resources/META-INF/microprofile-config.properties) inside the application. However, some configuration properties are needed before during deployment before this file is read. Therefore we also supply the same file externally using the `--systemproperties` command line option.

The following properties are defined by default:

```
mq.username=guest
mq.password=guest
mq.addressList=localhost:7676
mq.queue.name=TESTQ
```

They are referenced in the JMS annotations using the `${MPCONFIG=property.name}` reference.

You can redefine the properties if you specify

* a system property with that name
* an environment variable with that name but `.` replaced by `_`, e.g. `my_username`
* a property defined by any MicroProfile config source provided by Payara Micro or your application