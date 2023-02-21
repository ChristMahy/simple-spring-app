package cmahy.springapp.resourceserver.controller.rest;

import cmahy.springapp.resourceserver.domain.TacoOrder;
import cmahy.springapp.resourceserver.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@RestController
@RequestMapping(
    path = "/api/taco-order",
    produces = { APPLICATION_JSON_VALUE, TEXT_XML_VALUE }
)
public class TacoOrderController {
    private final OrderRepository orderRepository;

    public TacoOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PutMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public TacoOrder modifyOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody TacoOrder order
    ) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public TacoOrder partialUpdateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody TacoOrder updatedOrder
    ) {
        TacoOrder initialOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found !"));

        if (updatedOrder.getDeliveryCity() != null) {
            initialOrder.setDeliveryCity(updatedOrder.getDeliveryCity());
        }

        if (updatedOrder.getDeliveryName() != null) {
            initialOrder.setDeliveryName(updatedOrder.getDeliveryName());
        }

        if (updatedOrder.getDeliveryState() != null) {
            initialOrder.setDeliveryState(updatedOrder.getDeliveryState());
        }

        if (updatedOrder.getDeliveryZip() != null) {
            initialOrder.setDeliveryZip(updatedOrder.getDeliveryZip());
        }

        if (updatedOrder.getDeliveryStreet() != null) {
            initialOrder.setDeliveryStreet(updatedOrder.getDeliveryStreet());
        }

        if (updatedOrder.getCcCVV() != null) {
            initialOrder.setCcCVV(updatedOrder.getCcCVV());
        }

        if (updatedOrder.getCcNumber() != null) {
            initialOrder.setCcNumber(updatedOrder.getCcNumber());
        }

        if (updatedOrder.getCcExpiration() != null) {
            initialOrder.setCcExpiration(updatedOrder.getCcExpiration());
        }

        return orderRepository.save(initialOrder);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepository.findById(orderId).ifPresent(initialOrder ->
                orderRepository.deleteById(initialOrder.getId())
        );
    }
}
