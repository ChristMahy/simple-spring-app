package cmahy.springapp.controller;

import cmahy.springapp.domain.TacoOrder;
import cmahy.springapp.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import static cmahy.springapp.config.security.AuthorizationConstant.ROLE_USER;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('" + ROLE_USER + "')")
    public String orderForm() {
        return "order-form";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('" + ROLE_USER + "')")
    public String processOrder(
        @Valid TacoOrder tacoOrder,
        Errors errors,
        SessionStatus sessionStatus
    ) {
        if (errors.hasErrors()) {
            return "order-form";
        }

        log.info("Order submitted: {}", tacoOrder);

        orderRepository.save(tacoOrder);

        sessionStatus.setComplete();

        return "redirect:/";
    }
}
