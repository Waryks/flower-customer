package org.soa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.soa.entity.Purchase;

@ApplicationScoped
public class PurchaseRepository implements PanacheRepository<Purchase> {
}
