package org.soa.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.soa.dto.CustomerDto;
import org.soa.service.CustomerService;

import java.net.URI;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CustomerResource {
    @Inject
    CustomerService customerService;
    @POST
    @Path("/register")
    public Response register(CustomerDto customerDto) {
        return Response.created(URI.create(customerService.createCustomer(customerDto).toString())).build();
    }

    @POST
    @RolesAllowed("admin")
    @Path("/{customerId}/cashier")
    public Response createCashier(@PathParam("customerId") Long customerId) {
        return Response.created(URI.create(customerService.createCashier(customerId).toString())).build();
    }

    @POST
    @Path("/login")
    public Response login(CustomerDto customerDto){
        return Response.ok(customerService.findCustomer(customerDto)).build();
    }
}
