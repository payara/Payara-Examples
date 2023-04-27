package fish.payara.controllers;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class CustomBusinessException extends ResponseProcessingException {

	private String errorCode;
	private String errorMessage;
	private String errorPayload;

	public CustomBusinessException(final String errorCode, final String message, final String errorPayload) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(errorCode).build(), message);
		this.errorMessage = message;
		this.errorPayload = errorPayload;
		this.errorCode = errorCode;
	}


}
