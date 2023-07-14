# BudgetJourney - Discover Your Dream City Within Your Budget

Imagine you want to visit a city and have a specific budget in mind. BudgetJourney is an app designed to suggest multiple points of interest within the city, tailored to fit your budget constraints.

This Jakarta EE application leverages the OpenAI GPT API to generate recommendations for points of interest. To optimize costs and reduce the volume of requests to the GPT API, all previous suggestions are cached using the Payara Data Grid through JCache.

You can also generate images by entering some prompts. The app uses the same API powering OpenAI's Dalle image generation service. 

## Prerequisite

* Java 1/17 (Java EE 8, Jakarta EE 10): https://sdkman.io


## Configuration

Open the `microprofile-config.properties` file and provide the following configuration settings:

1. OpenAI API key:
    ```shell
    openai.key={YOUR_API_KEY}
    ```



## Usage

Start the app from a terminal:
```shell
docker compose up -d
mvn mvn clean package -DskipTests  -Pproduction && /usr/bin/cp -f target/*.war deployments 
```

You can then access the application from : http://localhost:8080/jee-chatgpt   
You can also play with app deployed to the cloud on the Payara Cloud native Jakarta EE app deployment service here : https://start-dev-21bc180d.payara.app/jee-chatgpt/

<img width="1505" alt="tokyo" src="https://raw.githubusercontent.com/payara/Payara-Examples/Payara6/blog-examples/jee-chatgpt/Screenshot_20230714_094645.png">

To experience what BudgetJourney has to offer, simply provide a city name and budget limit, or enter some prompts to have AI generated images based on the prompt. 

Please note that it may take 30 seconds or more for the OpenAI GPT to generate suggestions. However, to enhance efficiency, all previous suggestions are cached using JCache and will be served from there whenever you inquire about the same city and budget again.
