package org.soa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "flower")
@Getter
@Setter
public class Flower {
    @Id
    @Basic(optional = false)
    @Column(name = "flower_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flower_SEQ")
    private Long flowerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "availability_status", nullable = false)
    private Boolean availabilityStatus = false;

}