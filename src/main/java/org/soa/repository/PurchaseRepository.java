package org.soa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.soa.entity.Customer;
import org.soa.entity.Purchase;

import java.util.List;

@ApplicationScoped
public class PurchaseRepository implements PanacheRepository<Purchase> {
    public List<Purchase> findByCustomer(Long customerId){
        return find("customer.customerId", customerId).stream().toList();
    }
}
