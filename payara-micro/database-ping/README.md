# database-ping

A project showing how to deploy an application without it's
required database being available. Working since 173.

This project was featured in this blog: http://waiting-for-blog-to-be-published.

Build the project and Docker Image by running `./build.sh`.

Run the Docker images by running `./start.sh`.

Once the images are running, go to `localhost:8080/ping`.
A successful connection will return: `Connection valid: true`.
An invalid connection will return: `Connection valid: false`.

Stop the images by running `./stop.sh`.

Delete the custom Payara Micro image by running `./delete.sh`.
