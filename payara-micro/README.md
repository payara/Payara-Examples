# Payara Micro Examples

A number of projects demostrating features of Payara Micro

## simplest-bootstrap

The simplest bootstrap application to embed Payara Micro into your application

## deployment-example

A simple Payara Micro showing how to deploy the payara-micro-examples.war file

## autobind-cluster

A simple Payara Micro example showing how to use autobind to run many instances of Payara Micro
without having to worry about port numbers.

## cdi-clustered-events

A simple demonstration of using Payara Micro's clustered CDI event bus to send CDI events
from one Payara Micro instance to another. See http://www.payara.co.uk/payara-micro-clustered-cdi-event-bus

## [database-ping](database-ping)

An example Docker project showing how to deploy an application without it's required database being
available.

## datasource-example

An example showing how to deploy a DataSource with the definition embedded in the web.xml
using JavaEE 7 standard features

## jpa-datasource-example

An example showing how to deploy a datasource defined in the web.xml and use the
datasource from JPA.

## Spring boot JPA JSF Example

An example Spring Boot application using JSF and JPA that runs on Payara Micro

## Spring Boot REST Example

An example Spring Boot REST service that runs on Payara Micro

## JMS Client Example

An example demonstrating sending and receiving messages on an ActiveMQ using Payara Micro.

## Stock Ticker Demo

Demo of using the Clustered CDI Event Bus and WebSockets to update a graph in real time from data fired across the Payara Cluster as a CDI event.
