# Deploy and Manage a Scalable Payara Cluster on Azure

This repo contains templates that can deploy a [Payara](https://payara.fish) Deployment Group consisting of one admin server and a number of instances.

# How to deploy

## Azure CLI (Azure Cloud Shell recommended)

You'll want to use Azure CLI to deploy the templates, and [Azure Cloud Shell](https://shell.azure.com/) might be best
(Azure CLI is already installed and configured), if you don't already have a machine installed and set up with Azure CLI.
Make sure that your subscription is set correctly by checking `az account show` result (you can set your subscription by
`az account set -s <your_desired_subscription_id>`).

## Get the templates

The templates are hosted on Github. Check out the templates into your cloud shell directory:

```
git clone https://github.com/rdebusscher/payara_azure_template.git
```

Remark: You need to have the `azuredeploy.json` file locally but it is referring to the Github Repo location (see also `_artifactsLocation` parameter in the json file) because the Azure Resource manager is loading the referenced files from there.

## Create an SSH key pair

We need an SSH key pair to configure deployed VMs for remote SSH access. Create a key pair using the following command:

```
ssh-keygen -t rsa -N "" -f </path/to/my_payara_id_rsa>
```

Feel free to replace the file name (`/path/to/my_payara_id_rsa`) to your desired name and location.

## Create a resource group

We should create an Azure resource group where we'll deploy the templates:

```
az group create -g my_payara_rg -l <desired_azure_region>
```

## Deploy templates

Use the following command to deploy the templates. If you used your own names for files and the resource group,
make sure to replace them in the command. You'll also need to provide a good password for the `payaraAdminServerPassword` 
parameter (you'll use this parameter to access your deployed Payara admin server).

```
az group deployment create -g my_payara_rg -n payara_azure --template-file azuredeploy.json --parameters sshPublicKey="$(cat /path/to/my_payara_id_rsa.pub)" sshPrivateKey="$(cat /path/to/my_payara_id_rsa | base64 -w 0)" payaraAdminServerPassword=<your_desired_payara_admin_password>
```

Remark : On MacOSX, the command `base64` doesn't need any parameters.

## Accessing the deployed resources (controller VM and Payara site)

Deployed controller VM (where Payara Admin Server runs) and the Payara site (application server cluster) can be accessed as follows.

First of all, you'll need to get the domain name of the controller VM and the load balancer:

```
ctlrDNS=$(az group deployment show -g my_payara_rg -n payara_azure --query properties.outputs.controllerDNS.value)
lbDNS=$(az group deployment show -g my_payara_rg -n payara_azure --query properties.outputs.loadBalancerDNS.value)
```

An example controller DNS is like `controller-pubip-xyz123.someregion.cloudapp.azure.com`. An example load balancer DNS is
like `lb-xyz123.someregion.cloudapp.azure.com`.

You can login to the controller VM using SSH with the following command:

```
ssh -i my_payara_id_rsa payaraadmin@$ctlrDNS
```

The Payara admin server web console can be accessed by browsing `https://$ctlrDNS:4848`. You should use `admin` as the login ID and the `payaraAdminServerPassword` parameter value you used in the `az group deployment create` command above as the login password.

The deployed Payara application site can be accessed by browsing `https://$lbDNS` or `http://$lbDNS`.
