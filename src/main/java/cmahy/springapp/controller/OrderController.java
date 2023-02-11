package cmahy.springapp.controller;

import cmahy.springapp.config.security.UserSecurityDetails;
import cmahy.springapp.domain.TacoOrder;
import cmahy.springapp.properties.TacoOrderProperties;
import cmahy.springapp.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import static cmahy.springapp.config.security.AuthorizationConstant.ROLE_USER;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderRepository orderRepository;
    private final TacoOrderProperties tacoOrderProperties;

    public OrderController(
        OrderRepository orderRepository,
        TacoOrderProperties tacoOrderProperties
    ) {
        this.orderRepository = orderRepository;
        this.tacoOrderProperties = tacoOrderProperties;
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
