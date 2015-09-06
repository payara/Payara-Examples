# rest-jcache

A simple example consisting of a REST service that stores JSON into the cache based on a key
The example uses the standard JCache annotations to retrieve and store data into a cache

Deploy onto Payara Micro using
```shell
java -jar payara-micro.jar --autoBindHttp --deploy rest-jcache-1.0-SNAPSHOT.war
```

Once deployed on Payara Micro test insert data using;

```shell
curl -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d "{data}" http://127.0.0.1:8080/rest-jcache-1.0-SNAPSHOT/webresources/cache?key=test
```

Retrieve data using
```shell
curl http://127.0.0.1:8080/rest-jcache-1.0-SNAPSHOT/webresources/cache?key=test
```

Also run a number of Payara Micro nodes and retrieve the same key from each and you should receive the same data as the cache is shared across all the Payara Micro instances