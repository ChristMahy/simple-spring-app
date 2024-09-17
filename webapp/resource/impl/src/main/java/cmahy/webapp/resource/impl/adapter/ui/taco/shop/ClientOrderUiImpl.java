package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.shop.ClientOrderUi;
import cmahy.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.webapp.taco.shop.kernel.application.command.ReceiveNewClientOrderCommand;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientOrderUiImpl implements ClientOrderUi {

    private static final Logger LOG = LoggerFactory.getLogger(ClientOrderUiImpl.class);

    private final ReceiveNewClientOrderCommand receiveCommand;

    public ClientOrderUiImpl(
        ReceiveNewClientOrderCommand receiveCommand
    ) {
        this.receiveCommand = receiveCommand;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public String current(Model model) {
        if (!model.containsAttribute(TACOS) || ((List<TacoInputVo>) model.getAttribute(TACOS)).isEmpty()) {
            return "redirect:" + TacoUriConstant.Design.DESIGN_BASE_URL;
        }

        if (!model.containsAttribute(TACO_ORDER_SESSION)) {
            model.addAttribute(TACO_ORDER_SESSION, new ClientOrderInputVo(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                new ArrayList<>(0)
            ));
        }

        model.addAttribute(TACO_ORDER, model.getAttribute(TACO_ORDER_SESSION));

        return "taco-shop/client-order-form";
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    @Transactional
    public String saveOrder(
        Model model,
        ClientOrderInputVo tacoOrder,
        Errors errors,
        List<TacoInputVo> tacos,
        SessionStatus sessionStatus,
        UserSecurityDetails currentUserSecurityInputApiVo
    ) throws UserNotFoundException, IngredientNotFoundException, RequiredException {
        if (errors.hasErrors()) {
            return "taco-shop/client-order-form";
        }

        LOG.info("Order submitted: <{}>", tacoOrder);

        receiveCommand.execute(
            new ClientOrderInputVo(
                tacoOrder.deliveryName(),
                tacoOrder.deliveryStreet(),
                tacoOrder.deliveryCity(),
                tacoOrder.deliveryState(),
                tacoOrder.deliveryZip(),
                tacoOrder.ccNumber(),
                tacoOrder.ccExpiration(),
                tacoOrder.ccCVV(),
                tacos
            ),
            currentUserSecurityInputApiVo.userSecurity().id()
        );

        sessionStatus.setComplete();

        return "redirect:" + TacoUriConstant.BASE_URL;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public String saveOrder(
        Model model,
        ClientOrderInputVo tacoOrder
    ) {
        model.addAttribute(TACO_ORDER_SESSION, tacoOrder);

        return "redirect:" + TacoUriConstant.Design.DESIGN_BASE_URL;
    }
}
