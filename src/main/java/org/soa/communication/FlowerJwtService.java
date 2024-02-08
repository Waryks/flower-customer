package org.soa.communication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/flower-details")
@RegisterRestClient(configKey = "flower-details")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface FlowerJwtService {
    @GET
    @Path("notifications/jwt/{role}/{customerId}")
    public Response createJwt(@PathParam("role") String role, @PathParam("customerId") Long customerId);
}
