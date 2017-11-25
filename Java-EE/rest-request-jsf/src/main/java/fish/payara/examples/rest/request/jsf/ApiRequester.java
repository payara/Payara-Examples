package fish.payara.examples.rest.request.jsf;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource.Builder;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@Dependent
public class ApiRequester {

    private HttpServletRequest request;

    /**
     * Gets the details of the request that was made to the JSF page to
     * initialise this bean.
     */
    @PostConstruct
    private void getContext() {
        request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
    }

    /**
     * Makes a GET request to a specified endpoint for a local API. E.g. to get
     * a String from the URL http://api.localhost/hello call get("/hello",
     * String.class).
     *
     * @param uri The endpoint to get the result of.
     * @param returnType The type of Object expected from the endpoint.
     * @return The result.
     */
    public <T> T get(String uri, Class<T> returnType) {
        return createResource(uri).get(returnType);
    }

    /**
     * Makes a POST request to a specified endpoint for a local API. E.g. to get
     * a String from the URL http://api.localhost/hello call post("/hello", "",
     * String.class).
     *
     * @param uri The endpoint to get the result of.
     * @param requestEntity The request body to post to the endpoint.
     * @param returnType The type of Object expected from the endpoint.
     * @return The result.
     */
    public <T> T post(String uri, Object requestEntity, Class<T> returnType) {
        return createResource(uri).post(returnType, requestEntity);
    }

    private Builder createResource(String uri) {
        // Append "api." to start of URL
        String apiUrl = request.getRequestURL().toString().replaceAll("://", "://api.");
        // Get a request builder object from API link (remove leading slash from parameter to prevent double slash)
        return Client.create().resource(apiUrl + uri.replaceAll("^/", "")).getRequestBuilder();
    }

}
