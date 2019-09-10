# Deploy an application on AWS Elastic Beanstalk using a Docker image from a Docker repository

AWS Elastic Beanstalk (EB) allows deploying applications using an existing Docker image in a Docker repository. EB will need a `Dockerrun.aws.json` configuration file to deploy and run the application in one or more Docker containers. This JSON file specifies the name of the Docker image to use and overrides the default configuration of that image. EB will automatically scale and create/drop Docker containers as needed. EB also automatically creates a load-balancer in front of the containers.

The project needs to contain a `Dockerrun.aws.json` file and all the resources needed to build the Docker image. You can start with this directory which contains a sample `Dockerrun.aws.json` file which will deploy an empty Payara Server using an unmodified Payara Server Docker image downloaded from the public Docker Hub registry.

Instead of the default Payara Server Docker image, you can build a custom Docker image based on the `Dockerfile` described in the `payara-server-docker-aws-eb` example, push it to Docker Hub and point `Dockerrun.aws.json` file to your custom Docker image in Docker Hub. With some more work, it's also possible to use a private AWS Docker registry with the AWS Elastic Container Registry (ECR).
___

NOTE: In order to deploy this example, you need to install the `eb` command line tool provided by AWS. You can find the documentation and installation instructions for the `eb` tool in the AWS EB documentation here: [Install the EB CLI Using Setup Scripts](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html)

___

In order to build and deploy the application on EB, execute the following commands:

1. Connect the local project to an AWS account, region and existing or new application in EB:

```
eb init
```

When asked, create a new application and select "Docker" as the platform type. This instructs EB to create a single-docker application.

2. Create an environment and deploy your application there:

```
eb create
```

If you need to deploy a new version of your application, rebuild and push the Docker image into Docker registry and redeploy changes with:

```
eb deploy
```

You can then access your application running in EB either from the AWS Management Console or by running `eb open`.
