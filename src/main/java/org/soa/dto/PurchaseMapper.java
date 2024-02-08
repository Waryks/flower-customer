package org.soa.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.soa.entity.Flower;
import org.soa.entity.Purchase;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PurchaseMapper {
    @Mapping(source = "flower.flowerId", target = "flowerId")
    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "purchaseId", target = "purchaseId")
    PurchaseDto toDto(Purchase purchase);

    @Mapping(source = "flower.flowerId", target = "flowerId")
    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "purchaseId", target = "purchaseId")
    List<PurchaseDto> toDtoList(List<Purchase> purchase);

    @Mapping(target = "flower.flowerId", source = "flowerId")
    @Mapping(target = "customer.customerId", source = "customerId")
    @Mapping(target = "purchaseId", source = "purchaseId")
    Purchase toEntity(PurchaseDto purchaseDto);
}
