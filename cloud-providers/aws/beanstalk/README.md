# AWS Elastic Beanstalk Examples

Deploying and configuring applications with Payara Platform in a Docker container using AWS Elastic Beanstalk.

In order to deploy the examples, you need to install the `eb` command line tool provided by AWS. You can find the documentation and installation instructions for the `eb` tool in the AWS EB documentation here: [Install the EB CLI Using Setup Scripts](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html)

## Using Dockerfile

AWS Elastic Beanstalk (EB) allows deploying applications using a Dockerfile. This is very convenient in development because a new version can be deployed directly to EB with a single command. Look into the directory `payara-server-docker-aws-eb` for an example and instructions to use it.

## Deploy using a Docker image from a Docker repository

AWS Elastic Beanstalk also allows deploying prebuilt Docker images. This requires a `Dockerrun.aws.json` file that specifies the name of the Docker image and overrides the default configuration for that image. Look into the directory `aws-eb-docker-remote-deployment` for an example and instructions to use it.