# Deploy an application on AWS Elastic Beanstalk using Dockerfile


AWS Elastic Beanstalk (EB) allows deploying applications using a Dockerfile. EB will use the Dockerfile to build a Docker image and use it to deploy and run the application in one or more Docker containers. EB will automatically scale and create/drop Docker containers as needed. EB also automatically creates a load-balancer in front of the containers.

The project needs to contain a Dockerfile and all the resources needed to build the Docker image. You can start with this directory which contains `Dockerfile` and a sample Maven Web Application project.

The file `Dockerfile` is configured to use Payara Server to run the application. In order to use Payara Micro, replace `Dockerfile` with the file `Dockerfile-Payara-Micro` and continue.

___

NOTE: In order to deploy this example, you need to install the `eb` command line tool provided by AWS. You can find the documentation and installation instructions for the `eb` tool in the AWS EB documentation here: [Install the EB CLI Using Setup Scripts](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html)

___

In order to build and deploy the application on EB, execute the following commands:

1. Build the application WAR package:

```
mvn clean package
```

2. Connect the local project to an AWS account, region and existing or new application in EB:

```
eb init
```

When asked, create a new application and select "Docker" as the platform type. This instructs EB to create a single-docker application.

1. Create an environment and deploy your application there:

```
eb create
```

If you need to deploy a new version of your application or you modified `Dockerfile`, you can redeploy changes with:

```
eb deploy
```

You can then access your application running in EB either from the AWS Management Console or by running `eb open`.
