##  Sample code for Jakarta EE with Google Cloud Platform
* You can find the full guide [here](https://www.payara.fish/resource/a-developer-guide-to-nosql-persistence-on-the-jakarta-ee-platform-with-google-firestore/)
* And a recording of the webinar [here](https://us02web.zoom.us/rec/share/a7o11YFynm5HC5qWXxmtGRHHUvKLJnwrz0S1vBqAPBRGOjTUCy2ibcDio5mE7IY0.jtkkY6hD36VJL6GE)

The application is a vanilla Jakarta EE app that is deployed on a Payara Platform Community 6 runtime. The server is bundled through docker in the Dockerfile and used in the docker-compose.yml file. You will need an authentication JSON file from Google Cloud Firestore from [here](https://console.firebase.google.com/project/jakarta-ee-adc2a/settings/serviceaccounts/adminsdk). Then copy the downloaded file to the src/main/resources folder.

To run, use the bundled script `run-app.sh`
* Start the bundled docker container with `./run-app.sh up`
* Then deploy with `./run-app.sh deploy`
* To stop the running instance, `./run-app.sh stop`