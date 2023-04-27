package fish.payara.controllers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Context
    UriInfo uriInfo;

    @Context
    ResourceInfo resourceInfo;
    @Override
    public Response toResponse(final ValidationException exception) {
        String s = resourceInfo.getResourceMethod().getDeclaredAnnotations()[0].toString().split("\\.")[3];

        final var jsonObject = Json.createObjectBuilder()
                .add("host", uriInfo.getAbsolutePath().getHost())
                .add("resource", uriInfo.getAbsolutePath().getPath())
                .add("resourceMethod", s)
                .add("title", "Validation Errors");

        final var jsonArray = Json.createArrayBuilder();

        JsonObjectBuilder errorObject = Json.createObjectBuilder()
                .add("message", "A validation exception occurred")
                .add("reason", exception.getLocalizedMessage());
        jsonArray.add(errorObject);
        JsonObject errorJsonEntity = jsonObject.add("errors", jsonArray.build()).build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorJsonEntity).build();
    }
}
