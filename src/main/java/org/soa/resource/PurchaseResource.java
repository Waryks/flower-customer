package org.soa.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.soa.dto.PurchaseDto;
import org.soa.service.PurchaseService;

@Path("/purchases")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PurchaseResource {
    @Inject
    PurchaseService purchaseService;
    @POST
    @Path("/")
    public Response purchaseFlowers(PurchaseDto purchaseDto) {
        purchaseService.buyFlowers(purchaseDto);
        return Response.accepted().build();
    }
}
