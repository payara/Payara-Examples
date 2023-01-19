# To run the app

* ```docker-compose up -d```
* ```mvn clean package -DskipTests && cp -rf target/*.war deployments```

Then you can make a GET request to

```http://localhost:8080/t11-jumpstart/api/hello-world/ping```  

This should return "Hello, World!"

You can also make a call to  
```http://localhost:8080/t11-jumpstart/api/hello-world/{name}```  

Where name is your name. This should return a JSON object with some greeting info.



### Let's get going!