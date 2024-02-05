package org.soa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "purchase")
@Getter
@Setter
public class Purchase{
    @Id
    @Basic(optional = false)
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "purchase_date")
    private OffsetDateTime purchaseDate;
}