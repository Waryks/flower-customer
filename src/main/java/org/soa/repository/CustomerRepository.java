package org.soa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.soa.entity.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
    public Long save(Customer customer) {
        persist(customer);
        return customer.getCustomerId();
    }

    public Customer findByEmail(String email){
        return find("email", email).firstResult();
    }
}
