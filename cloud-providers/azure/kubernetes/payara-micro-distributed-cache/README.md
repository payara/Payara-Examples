# Develop and test locally (with MiniKube, Docker Desktop, ...)

All commands should be executed from the _project root_.

## Build Maven Project

```
mvn clean package
```

## build Docker Image

```
docker build -t cluster-demo .
```

## Test Docker Image

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

## Test with Kubernetes

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

# Azure

## Create Container registry

Create a resource group if doesn't exist:

```
az group create --name myResourceGroup --location westeurope
```

Create a Docker repository:

```
az acr create --resource-group myResourceGroup --name payaratest --sku Basic
```

## Link docker with Azure Container registry

```
az acr login --name rubus
```

## Push Image to Azure Container registry

Label a Docker image with a tag in the required format:

```
docker tag cluster-demo:v1 payaratest.azurecr.io/cluster-demo:v1
```

Push (upload) the Docker image to Azure:

```
docker push payaratest.azurecr.io/cluster-demo:v1
```

## Create Kubernetes Cluster

Create a new cluster with the private Docker registry attached:

```
az aks create -n azure-k8s-cluster -g myResourceGroup --attach-acr payaratest
```

When created through the Portal, this can be used to attach the Container Registry:

```
az aks update -n azure-k8s-cluster -g myResourceGroup --attach-acr payaratest
```

## Link kubectl to the cluster in Azure

```
az aks get-credentials --resource-group myResourceGroup --name azure-k8s-cluster
```

## Deploy application resources

```
kubectl apply -f src/main/k8s/rbac.yaml
kubectl apply -f src/main/k8s/deployment.yaml
kubectl apply -f src/main/k8s/service.yaml
```

## Check and test

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

# Switching back kubectl config

Azure context for kubectl was created in its config. When we're finished working with Azure, we can remove the context:

```
kubectl config get-contexts

kubectl config use-context docker-desktop

kubectl config delete-context azure-k8s-cluster
```
