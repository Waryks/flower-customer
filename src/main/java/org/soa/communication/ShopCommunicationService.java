package org.soa.communication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/flower-shop")
@RegisterRestClient(configKey = "flower-shop")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface ShopCommunicationService {
    @GET
    @Path("/flowers/{flowerId}")
    Response findFlowerDetails(@PathParam("flowerId") Long flowerId);

    @PUT
    @Path("/flowerstocks/{flowerId}/{quantity}")
    public Response changeFlowerDetails(@PathParam("flowerId") Long flowerId, @PathParam("quantity") Integer quantity);

    @PUT
    @Path("/flowerstocks/refund/{flowerId}/{quantity}")
    public Response refundFlowerDetails(@PathParam("flowerId") Long flowerId, @PathParam("quantity") Integer quantity);
}
