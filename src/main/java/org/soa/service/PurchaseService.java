package org.soa.service;

import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.soa.communication.ShopCommunicationService;
import org.soa.dto.FlowerDto;
import org.soa.dto.PurchaseDto;
import org.soa.dto.PurchaseMapper;
import org.soa.entity.Customer;
import org.soa.entity.Flower;
import org.soa.entity.Purchase;
import org.soa.exception.UserNotAuthorizedException;
import org.soa.exception.UserNotFoundException;
import org.soa.repository.PurchaseRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class PurchaseService {
    @Inject
    PurchaseRepository purchaseRepository;
    @Inject
    CustomerService customerService;
    @Inject
    PurchaseMapper purchaseMapper;
    @Inject
    @RestClient
    ShopCommunicationService shopCommunicationService;
    @Transactional
    public void buyFlowers(PurchaseDto purchaseDto) {
        Response response = shopCommunicationService.findFlowerDetails(purchaseDto.getFlowerId());
        FlowerDto flowerDto = null;
        if(response.getStatus() == Response.Status.OK.getStatusCode())
            flowerDto = response.readEntity(FlowerDto.class);
        Customer customer = customerService.findCustomerById(purchaseDto.getCustomerId());
        shopCommunicationService.changeFlowerDetails(purchaseDto.getFlowerId(), purchaseDto.getQuantity());
        Purchase purchase = new Purchase();
        Flower flower = new Flower();
        flower.setFlowerId(purchaseDto.getFlowerId());
        purchase.setFlower(flower);
        purchase.setPurchaseDate(OffsetDateTime.now());
        purchase.setQuantity(purchaseDto.getQuantity());
        purchase.setCustomer(customerService.findCustomerById(purchaseDto.getCustomerId()));
        purchaseRepository.persist(purchase);
    }

    @Transactional
    public void refundFlowers(Long purchaseId, Long customerTokenId, Set<String> groups) {
        Purchase purchase = purchaseRepository.findByIdOptional(purchaseId).orElseThrow(() -> new UserNotFoundException("No purchase found with that id"));
        if(!Objects.equals(purchase.getCustomer().getCustomerId(), customerTokenId) && (!groups.contains("admin") || !groups.contains("cashier")))
            throw new UserNotAuthorizedException("Not allowed!");
        shopCommunicationService.refundFlowerDetails(purchase.getFlower().getFlowerId(), purchase.getQuantity());
        purchaseRepository.delete(purchase);
    }

    @Incoming("flower-shop-kafka")
    public void receive(String message){
        Log.info(message);
    }
    @Incoming("flower-shop")
    public void process(JsonObject msg){
        Log.info(msg.toString());
    }

    public List<PurchaseDto> getPurchases(Long customerId) {
        List<Purchase> purchase = purchaseRepository.findByCustomer(customerId);
        if (purchase == null || purchase.isEmpty())
            return new ArrayList<>();
        return purchaseMapper.toDtoList(purchase);
    }
}
