package org.soa.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link org.soa.entity.Customer}
 */
@Getter
@Setter
public class CustomerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String repassword;
}