package org.soa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.soa.communication.FlowerJwtService;
import org.soa.communication.ShopCommunicationService;
import org.soa.dto.CustomerDto;
import org.soa.dto.FlowerDto;
import org.soa.entity.Customer;
import org.soa.exception.UserNotFoundException;
import org.soa.repository.CustomerRepository;

import java.util.Objects;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    @RestClient
    FlowerJwtService flowerJwtService;
    @Transactional
    public Long createCustomer(CustomerDto customerDto){
        if(customerDto.getName() == null || customerDto.getEmail() == null
                || customerDto.getPhone() == null || customerDto.getPassword() == null
                || customerDto.getRepassword() == null)
            throw new UserNotFoundException("Fill all the required fields!");
        if(!Objects.equals(customerDto.getPassword(), customerDto.getRepassword()))
            throw new UserNotFoundException("Passwords do not match!");
        if(customerDto.getName().isEmpty() || customerDto.getEmail().isEmpty()
                || customerDto.getPhone().isEmpty() || customerDto.getPassword().isEmpty()
                || customerDto.getRepassword().isEmpty())
            throw new UserNotFoundException("Fill all the required fields!");
        if(customerRepository.findByEmail(customerDto.getEmail()) != null)
            throw new UserNotFoundException("Email already used!");
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setPassword(customerDto.getPassword());
        customer.setPhone(customerDto.getPhone());
        customer.setEmail(customerDto.getEmail());
        customer.setRoles("customer");
        return customerRepository.save(customer);
    }

    public String findCustomer(CustomerDto customerDto) {
        if(customerDto.getEmail() == null || customerDto.getPassword() == null)
            throw new UserNotFoundException("Fill all the required fields!");
        Customer customer = customerRepository.findByEmail(customerDto.getEmail());
        if(customer == null || !Objects.equals(customer.getPassword(), customerDto.getPassword()))
            throw new UserNotFoundException("Customer not found or password is wrong!");
        Response response = flowerJwtService.createJwt(customer.getRoles(), customer.getCustomerId());
        return response.readEntity(String.class);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findByIdOptional(customerId).orElseThrow(() -> new UserNotFoundException("No customer found with that id"));
    }

    public Long createCashier(Long customerId) {
        Customer customer = customerRepository.findByIdOptional(customerId).orElseThrow(() -> new UserNotFoundException("No such customer"));
        customer.setRoles("cashier");
        return customerId;
    }
}
