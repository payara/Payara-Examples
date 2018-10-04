## Simple JPA Example

A simple JPA CRUD example with JAX-RS endpoint


## JAX-RS endpoints

### CREATE
```
curl -X POST http://localhost:8080/api/person --header "Content-Type: application/json" -d "{\"name\":\"Gaurav Gupta\", \"address\":\"Lucknow, India\"}"
```

### READ All
```
curl -X GET  http://localhost:8080/api/person
```

### READ Single
```
curl -X GET  http://localhost:8080/api/person/1
```

### UPDATE
```
curl -X PUT http://localhost:8080/api/person --header "Content-Type: application/json" -d "{\"name\":\"Gaurav\", \"id\":1, \"address\":\"Lucknow, India\"}"
```

### DELETE
```
curl -X DELETE  http://localhost:8080/api/person/1
```


