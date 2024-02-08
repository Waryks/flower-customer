package org.soa.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * DTO for {@link org.soa.entity.Purchase}
 */
@Getter
@Setter
public class PurchaseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long purchaseId;
    private Long flowerId;
    private Long customerId;
    private Integer quantity;
    private OffsetDateTime purchaseDate;
    private String roles;
}