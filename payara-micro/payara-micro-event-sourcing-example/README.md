# Payara Micro Event Sourcing Example

This example illustrates how to implement the [Event Sourcing Design Pattern](http://microservices.io/patterns/data/event-sourcing.html) by leveraging the [Payara Micro JCA Adapter for Apache Kafka](http://blog.payara.fish/payara-micro-jca-adapters-apache-kafka).

## Prerequisites

* JDK 8
* Apache Maven
* Payara Micro 5 (The latest available at the time of writing this README is: 5.183)


## Setting up

1. Download Apache Kafka at https://kafka.apache.org/downloads. 
2. Extract the Apache Kafka zip file into a directory of your choice.
3. Start the ZooKeeper instance included with Kafka
  * `./bin/zookeeper-server-start.sh ../config/zookeeper.properties`
4. Start Kafka
  * `./bin/kafka-server-start.sh ../config/server.properties`
3. Create the two topics necessary for the application
  * `./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic checkingacct-topic`
  * `./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic savingsacct-topic`

## Building

1. `mvn clean install` from the root directory of the application should build all the necessary source code.

## Deploying

The application consists of two microservice modules plus a third module containing a simple web based user interface, all three modules need to be deployed into separate instances of Payara Micro.

1. To deploy the checking account microservice module (alter paths as necessary):
  * `java -jar payara-micro-5.183.jar --deploy payara-micro-micro-event-sourcing-example/checking-acct-service/target/dependency/kafka-rar-0.1.0.rar --deploy payara-micro-micro-event-sourcing-example/checking-acct-service/target/checking-acct-service-1.0 --noCluster`
2. To deploy the savings account module (alter paths as necessary):
  * `java -jar payara-micro-5.183.jar --deploy payara-micro-micro-event-sourcing-example/savings-acct-service/target/dependency/kafka-rar-0.1.0.rar --deploy payara-micro-micro-event-sourcing-example/savings-acct-service/target/savings-acct-service-1.0 --port 9080 --noCluster`
3. To deploy the user interface module (alter paths as necessary):
  * `java -jar payara-micro-5.183.jar --deploy payara-micro-micro-event-sourcing-example/fundtransferui/target/dependency/kafka-rar-0.1.0.rar --deploy payara-micro-micro-event-sourcing-example/fundtransferui/target/fundtransferui-1.0 --port 10080 --noCluster`

## Running the example

Once the modules have been deployed as explained in the previous section, point your browser to http://localhost:10080/fundtransferui-1.0/.

## Additional notes

There will be two buttons in the user interface: 
* One will transfer funds from a savings account to a checking account.
* The other button will simulate an error condition while tranferring funds, to illustrate how to generate a compensating transaction (each microservice module has its own database, therefore two-phase commit is not possible).

To view data in the databases, connect using any database client using the following JDBC URLs (no username or password needed):

* Checking: `jdbc:h2:tcp://127.0.1.1:9122/~/checking_db`
* Savings: `jdbc:h2:tcp://127.0.1.1:9123/~/savings_db`

The savings and checking microservice modules will automatically create and populate their databases the first time they are deployed. To reset data in the database, simply delete the files `checking_db.mv.db` and `savings_db.mv.db` in your home directory and redeploy both modules.
