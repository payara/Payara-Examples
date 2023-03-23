package fish.payara.configuration;

import java.util.Set;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse(final ConstraintViolationException exception) {

		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

		final var jsonObject = Json.createObjectBuilder()
				.add("host", uriInfo.getAbsolutePath().getHost())
				.add("resource", uriInfo.getAbsolutePath().getPath())
				.add("title", "Validation Errors");

		final var jsonArray = Json.createArrayBuilder();

		for (final var constraint : constraintViolations) {

			String className = constraint.getLeafBean().toString().split("@")[0];
			String message = constraint.getMessage();
			String propertyPath;
			String[] split = constraint.getPropertyPath().toString().split("\\.");

			if (split.length == 1) {
				propertyPath = split[0];
			} else {
				propertyPath = split[2];
			}

			JsonObject jsonError = Json.createObjectBuilder()
					.add("class", className)
					.add("field", propertyPath)
					.add("violationMessage", message)
					.build();
			jsonArray.add(jsonError);

		}

		JsonObject errorJsonEntity = jsonObject.add("errors", jsonArray.build()).build();

		return Response.status(Response.Status.BAD_REQUEST).entity(errorJsonEntity).build();
	}
}
