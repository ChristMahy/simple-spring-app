package cmahy.springapp.resourceserver.controller;

import cmahy.springapp.resourceserver.config.security.UserSecurityDetails;
import cmahy.springapp.resourceserver.domain.TacoOrder;
import cmahy.springapp.resourceserver.properties.OrderProperties;
import cmahy.springapp.resourceserver.repository.OrderRepository;
import cmahy.springapp.resourceserver.config.security.AuthorizationConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProperties tacoOrderProperties;

    public OrderController(
        OrderRepository orderRepository,
        OrderProperties tacoOrderProperties
    ) {
        this.orderRepository = orderRepository;
        this.tacoOrderProperties = tacoOrderProperties;
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('" + AuthorizationConstant.ROLE_USER + "')")
    public String orderForm() {
        return "order-form";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('" + AuthorizationConstant.ROLE_USER + "')")
    public String processOrder(
        @Valid TacoOrder tacoOrder,
        Errors errors,
        SessionStatus sessionStatus,
        @AuthenticationPrincipal UserSecurityDetails userSecurityDetails
    ) {
        if (errors.hasErrors()) {
            return "order-form";
        }

        tacoOrder.setUser(userSecurityDetails.user());

        log.info("Order submitted: {}", tacoOrder);

        orderRepository.save(tacoOrder);

        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(
        @AuthenticationPrincipal UserSecurityDetails userSecurityDetails,
        Model model
    ) {
        model.addAttribute(
            "orders",
            orderRepository.findByUserOrderByPlacedAtDesc(
                userSecurityDetails.user(),
                PageRequest.of(0, tacoOrderProperties.getPageSize())
            )
        );

        return "order-list";
    }
}
