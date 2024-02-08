package org.soa.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class UserNotAuthorizedExceptionHandler implements ExceptionMapper<UserNotAuthorizedException> {
    @Override
    public Response toResponse(UserNotAuthorizedException userNotAuthorizedException) {
        return Response.status(Response.Status.FORBIDDEN).entity(userNotAuthorizedException.getMessage()).build();
    }
}
