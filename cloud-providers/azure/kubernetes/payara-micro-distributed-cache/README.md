# An example application to deploy in Azure Kubernetes Service (AKS)

Application features:

- The application will allow creating and storing data about users. Each user is comprised of a name, an organization, and a consecutive ID.
- The application will track the current consecutive number of users.
- The application will track the hostname of the pod's name, which runs the application instance that created the user. To verify that requests are routed to different instances in the cluster if there are more than 1. The application will allow access to all stored data via any application instaces, regardless which instance created the data

Our application will make use of the following set of APIs that are included in the Payara Platform in order to implement those features:

- Jakarta EE Web API (https://jakarta.ee/), which is a standard API provided by Payara Micro
- JCache API (https://jcp.org/en/jsr/detail?id=107), so that all users are cached in a distributed manner. If a user object is created by an instance that is part of the cluster, other members can access it too
- Payara Public API (https://docs.payara.fish/documentation/payara-server/public-api/), to have access to the @Clustered annotation which is proprietary of the Payara Platform. With this annotation, we can configure a @Singleton EJB as a "true" singleton, which means that only one instance of it exists across all the instances that are member of the cluster. This clustered singleton is used to generate IDs unique to the whole cluster

## Develop and test locally (with MiniKube, Docker Desktop, ...)

All commands should be executed from the _project root_.

### Build Maven Project

```
mvn clean package
```

### Build Docker Image

```
docker build -t cluster-demo .
```

### Test Docker Image

Start the application:

```
docker run -d -p 8080:8080 --name cluster-demo cluster-demo
```

Post data to the application:

```
curl -X POST -i http://localhost:8080/data -H 'Content-Type: application/json' -d '{"name" : "Rudy De Busscher", "organization" : "Payara Services Ltd."}'
```

Query the data:

```
curl http://localhost:8080/data/1
curl http://localhost:8080/data/all
```

Stop the application and remove its Docker container:

```
docker rm -f cluster-demo
```

### Test with Kubernetes

Deploy the application:

```
kubectl apply -f src/main/k8s/cluster-demo-local.yaml
```

Verify the pods and services were created:

```
kubectl get pods
kubectl get svc
```

Post data to the application:

```
curl -X POST -i http://localhost:30080/data -H 'Content-Type: application/json' -d '{"name" : "Rudy De Busscher", "organization" : "Payara Services Ltd."}'
curl -X POST -i http://localhost:30080/data -H 'Content-Type: application/json' -d '{"name" : "Ondrej Mihalyi", "organization" : "Payara Services Ltd."}'
```

Query the data:

```
curl http://localhost:30080/data/1
curl http://localhost:30080/data/all
```

## Azure

### Create Container registry

Create a resource group if doesn't exist:

```
az group create --name myResourceGroup --location westeurope
```

Create a Docker repository:

```
az acr create --resource-group myResourceGroup --name payaratest --sku Basic
```

### Link docker with Azure Container registry

```
az acr login --name payaratest
```

### Push Image to Azure Container registry

Label a Docker image with a tag in the required format:

```
docker tag cluster-demo:v1 payaratest.azurecr.io/cluster-demo:v1
```

Push (upload) the Docker image to Azure:

```
docker push payaratest.azurecr.io/cluster-demo:v1
```

### Create Kubernetes Cluster

Create a new cluster with the private Docker registry attached:

```
az aks create -n azure-k8s-cluster -g myResourceGroup --attach-acr payaratest
```

When created through the Portal, this can be used to attach the Container Registry:

```
az aks update -n azure-k8s-cluster -g myResourceGroup --attach-acr payaratest
```

### Link kubectl to the cluster in Azure

```
az aks get-credentials --resource-group myResourceGroup --name azure-k8s-cluster
```

### Deploy application resources

```
kubectl apply -f src/main/k8s/rbac.yaml
kubectl apply -f src/main/k8s/deployment.yaml
kubectl apply -f src/main/k8s/service.yaml
```

### Check and test

List Kubernetes resources:

```
kubectl get pods
kubectl get svc
```

```
curl http://52.137.58.77:8080/pod
```

Post data to the application:

```
curl -X POST -i http://52.137.58.77:8080/data -H 'Content-Type: application/json' -d '{"name" : "Rudy De Busscher", "organization" : "Payara Services Ltd."}'
curl -X POST -i http://52.137.58.77:8080/data -H 'Content-Type: application/json' -d '{"name" : "Ondrej Mihalyi", "organization" : "Payara Services Ltd."}'
```

Query the data:

```
curl http://52.137.58.77:8080/data/all
```

## Switching back kubectl config

Azure context for kubectl was created in its config. When we're finished working with Azure, we can remove the context:

```
kubectl config get-contexts

kubectl config use-context docker-desktop

kubectl config delete-context azure-k8s-cluster
```
