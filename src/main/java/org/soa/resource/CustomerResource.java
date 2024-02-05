package org.soa.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @Path("/login")
    public Response login(CustomerDto customerDto){
        customerService.findCustomer(customerDto);
        return Response.ok("Successful!").build();
    }
}
