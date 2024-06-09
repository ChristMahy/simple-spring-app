package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.adapter.user.mapper.id.UserApiIdMapper;
import cmahy.webapp.resource.impl.application.taco.shop.command.ReceiveNewClientOrderCommand;
import cmahy.webapp.resource.taco.shop.vo.input.ClientOrderInputVo;
import cmahy.webapp.resource.taco.shop.vo.input.TacoInputVo;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.shop.ClientOrderUi;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientOrderUiImpl implements ClientOrderUi {

    private static final Logger LOG = LoggerFactory.getLogger(ClientOrderUiImpl.class);

    private final UserApiIdMapper userApiIdMapper;
    private final ReceiveNewClientOrderCommand receiveCommand;

    public ClientOrderUiImpl(
        UserApiIdMapper userApiIdMapper,
        ReceiveNewClientOrderCommand receiveCommand
    ) {
        this.userApiIdMapper = userApiIdMapper;
        this.receiveCommand = receiveCommand;
    }

    @Override
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
    public String saveOrder(
        Model model,
        ClientOrderInputVo tacoOrder,
        Errors errors,
        List<TacoInputVo> tacos,
        SessionStatus sessionStatus,
        UserSecurityDetails currentUserSecurityInputApiVo
    ) {
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
            userApiIdMapper.map(currentUserSecurityInputApiVo.userSecurity().id())
        );

        sessionStatus.setComplete();

        return "redirect:" + TacoUriConstant.BASE_URL;
    }

    @Override
    public String saveOrder(
        Model model,
        ClientOrderInputVo tacoOrder
    ) {
        model.addAttribute(TACO_ORDER_SESSION, tacoOrder);

        return "redirect:" + TacoUriConstant.Design.DESIGN_BASE_URL;
    }
}
