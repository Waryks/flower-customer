package org.soa.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.soa.dto.PurchaseDto;
import org.soa.service.PurchaseService;

import java.util.Objects;

@Path("/purchases")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PurchaseResource {
    @Inject
    PurchaseService purchaseService;
    @Inject
    JsonWebToken jwt;
    @POST
    @RolesAllowed({"customer","admin","cashier"})
    @Path("/")
    public Response purchaseFlowers(PurchaseDto purchaseDto) {
        String customerIdToken = jwt.getClaim("customerId").toString();
        if(!Objects.equals(customerIdToken, purchaseDto.getCustomerId().toString())
                && (!jwt.getGroups().contains("admin") || !jwt.getGroups().contains("cashier")))
            return Response.status(Response.Status.FORBIDDEN).build();
        purchaseService.buyFlowers(purchaseDto);
        return Response.accepted().build();
    }

    @GET
    @RolesAllowed({"customer","admin"})
    @Path("/{customerId}")
    public Response getPurchases(@PathParam("customerId") Long customerId){
        String customerIdToken = jwt.getClaim("customerId").toString();
        if(!Objects.equals(customerIdToken, customerId.toString()) && !jwt.getGroups().contains("admin"))
            return Response.status(Response.Status.FORBIDDEN).build();
        return Response.ok(purchaseService.getPurchases(customerId)).build();
    }

    @DELETE
    @RolesAllowed({"customer","admin","cashier"})
    @Path("/{purchaseId}")
    public Response purchaseFlowers(@PathParam("purchaseId") Long purchaseId) {
        Long customerIdToken = Long.getLong(jwt.getClaim("customerId").toString());
        purchaseService.refundFlowers(purchaseId, customerIdToken, jwt.getGroups());
        return Response.accepted().build();
    }
}
