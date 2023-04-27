##  Sample code for Jakarta EE with MongoDB
* You can find the full guide [here](https://www.payara.fish/resource/a-developer-guide-to-mongodb-with-morphia-and-jakarta-ee/)
* And a recording of the webinar [here](https://us02web.zoom.us/rec/share/REXv1DQwGxhTvx9d5vuNtIZw65E5pU2FxyPDnS-gekrsGdyHe8AoJMDVs22DdMqg.Goe0mXNkxPus2zrO)

The application is a vanilla Jakarta EE app that is deployed on a Payara Platform Community 6 runtime. The Jakarta EE runtime and MongoDB database are bundled through docker in the Dockerfile and used in the docker-compose.yml file.

To run, use the bundled script `run-app.sh`
* Start the bundled docker container with `./run-app.sh up`
* Then deploy with `./run-app.sh deploy`
* To stop the running instance, `./run-app.sh stop`