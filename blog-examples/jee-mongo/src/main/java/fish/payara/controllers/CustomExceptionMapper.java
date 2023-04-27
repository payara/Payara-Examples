package fish.payara.controllers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class CustomExceptionMapper implements ExceptionMapper<CustomBusinessException> {
	@Override
	public Response toResponse(final CustomBusinessException exception) {


		return null;
	}
}
