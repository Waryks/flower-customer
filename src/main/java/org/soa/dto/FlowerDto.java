package org.soa.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for Flowers
 */
@Getter
@Setter
public class FlowerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long flowerId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availabilityStatus;
}