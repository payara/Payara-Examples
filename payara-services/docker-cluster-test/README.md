## Test Application for the Advanced Docker guide and Payara Clustering scenarios on Docker

# What

The test application can be used in all clustered architectures to find out if the load balancing is working properly.

The application contains a servlet which returns the host name of the instance which handled the request.  
For testing the integration with the Data Grid (hazelcast), a Named cache entry (JCache) is used to increment a counter every time the endpoint is called. When called from different nodes, we can verify the data synchronization

The Max Heap size is also returned so that you can test out the resource assignments when performed in a Container environment for example.

# How to use

When the application is deployed, you can call the following URLs
- <host>/testapp/node -> Hostname and heapSize
- <host>/testapp/node/count -> Hostname, heapSize, and counter value (also increments the counter)

