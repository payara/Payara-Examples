# Java EE examples

Some simple examples of Java EE features in Payara. These are designed to demonstrate Payara specific features.

## [Clustered singleton](clustered-singleton)
Simple example of Clustered Singleton functionality available in Payara

## [EJB timer (ejb-timer)](ejb-timer)
A simple EJB Timer example

## [JBatch scheduling (jbatch-schedule)](jbatch-schedule)
A simple Batchlet JBatch scheduled with a timer EJB

## [Sample CRUD team-info app (team-info)](team-info)
A simple CRUD application that demonstrates using the Java Persistence API (JPA) to access a database.

## [Hibernate REST service Example (hibernate-example)](hibernate-example)
Payara Server example that demonstrates usage of Hibnernate with in memory H2 Databsase and REST endpoint

## [Simple REST service example (hello-world-rest)](hello-world-rest)
An example that hosts an endpoint `/hello`, which returns "Hello World!" and prints the endpoint information to the logs.

## [JSF REST client example (rest-request-jsf)](rest-request-jsf)
An example that uses JSF to create an HTML page that contains a string fetched from a REST endpoint at: `http://api.${host-name}/hello`.

## [Simple JPA example (simple-jpa-example)](simple-jpa-example)
A simple JPA CRUD example with JAX-RS endpoint

## [JCache API Samples (jcache)](jcache)
This module contains some samples demonstrating JCache API with Payara Server

## [Realm Security API Identity Stores (realm-identity-stores)](realm-identity-stores)
An example application to demonstrate using Payara Server realms as Java EE Security Identity Stores.
