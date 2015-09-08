#JCache CDI Interceptors Basic Sample#

The samples uses Payara 4.1.153 as an application server which can be downloaded from [here](http://www.payara.co.uk/downloads) 

The samples allows the user to generate a certain length (1-99) of random text and stores the result of the method in the cache the first time it is caled. The method is a very slow method. The next time the method is called for the same length of text, it is retrieved much faster from the cache. 

Make sure hazelcast in enabled on the server!

./asadmin set-hazelcast-configuration --enabled=true –target=server

To deploy the application to the app server you need to build the project using [Maven](https://maven.apache.org/).




