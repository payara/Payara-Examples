# Stock Ticker Application

This application consists of a simple EJB Timer that fires an event onto the Clustered CDI Event Bus
every second. The event consists of a single Stock POJO with a random number for the Stock Price

deploy this application on Payara Micro using;

```shell
java -jar payara-micro.jar --autobindhttp --deploy StockTicker-1.0-SNAPSHOT.jar
```

Once deployed it prints out a message whenever it fires an event;

```shell
Stock{symbol=PAYARA, description=, price=2.6473029469799902}
Stock{symbol=PAYARA, description=, price=53.18660982282327}
Stock{symbol=PAYARA, description=, price=4.84404008973881}
Stock{symbol=PAYARA, description=, price=92.9819267848963}
```
