package fish.payara.jeemongo;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

//@Provider
//@PreMatching
public class CustomRequestFilter implements ContainerRequestFilter {
	@Override
	public void filter(final ContainerRequestContext requestContext) {

		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String token = parseAuthToken(authHeader);
		if (!isValid(token)) {
			throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
		}

		String methodOverride = requestContext.getHeaderString("X-Http-Method-Override");
		if (methodOverride != null && !methodOverride.isBlank()) {
			requestContext.setMethod(methodOverride);
		}
	}

	private String parseAuthToken(String httpHeader) {
		if (httpHeader == null || httpHeader.isEmpty()) {
			throw new NotAuthorizedException("Bearer");
		}
		return httpHeader;
	}

	private boolean isValid(String token) {
		return token != null && !token.isBlank();
	}

}



