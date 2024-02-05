package org.soa.service;

import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hibernate.service.spi.InjectService;
import org.soa.communication.ShopCommunicationService;
import org.soa.dto.FlowerDto;
import org.soa.dto.PurchaseDto;
import org.soa.entity.Customer;
import org.soa.entity.Flower;
import org.soa.entity.Purchase;
import org.soa.repository.PurchaseRepository;

import java.time.OffsetDateTime;

@ApplicationScoped
public class PurchaseService {
    @Inject
    PurchaseRepository purchaseRepository;
    @Inject
    CustomerService customerService;
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

    @Incoming("flower-shop-kafka")
    public void receive(String message){
        Log.info(message);
    }
    @Incoming("flower-shop")
    public void process(JsonObject msg){
        Log.info(msg.toString());
    }
}
