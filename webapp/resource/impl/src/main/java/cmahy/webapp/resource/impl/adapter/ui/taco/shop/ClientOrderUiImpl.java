package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input.ClientOrderInputUiToAppMapper;
import cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input.TacoInputUiToAppMapper;
import cmahy.webapp.resource.impl.adapter.user.mapper.id.UserApiIdMapper;
import cmahy.webapp.resource.impl.application.taco.shop.command.ReceiveNewClientOrderCommand;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.shop.ClientOrderUi;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputUiVo;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputUiVo;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
public class ClientOrderUiImpl implements ClientOrderUi {

    private static final Logger LOG = LoggerFactory.getLogger(ClientOrderUiImpl.class);

    private final ClientOrderInputUiToAppMapper clientOrderInputMapper;
    private final UserApiIdMapper userApiIdMapper;
    private final TacoInputUiToAppMapper tacoInputMapper;
    private final ReceiveNewClientOrderCommand receiveCommand;

    public ClientOrderUiImpl(
        ClientOrderInputUiToAppMapper clientOrderInputMapper,
        UserApiIdMapper userApiIdMapper,
        TacoInputUiToAppMapper tacoInputMapper,
        ReceiveNewClientOrderCommand receiveCommand
    ) {
        this.clientOrderInputMapper = clientOrderInputMapper;
        this.userApiIdMapper = userApiIdMapper;
        this.tacoInputMapper = tacoInputMapper;
        this.receiveCommand = receiveCommand;
    }

    @Override
    public String current(Model model) {
        if (!model.containsAttribute(TACOS) || ((List<TacoInputUiVo>) model.getAttribute(TACOS)).isEmpty()) {
            return "redirect:" + TacoUriConstant.Design.DESIGN_BASE_URL;
        }

        if (!model.containsAttribute(TACO_ORDER_SESSION)) {
            model.addAttribute(TACO_ORDER_SESSION, ClientOrderInputUiVo.createEmpty());
        }

        model.addAttribute(TACO_ORDER, model.getAttribute(TACO_ORDER_SESSION));

        return "taco-shop/client-order-form";
    }

    @Override
    public String saveOrder(
        Model model,
        ClientOrderInputUiVo tacoOrder,
        Errors errors,
        List<TacoInputUiVo> tacos,
        SessionStatus sessionStatus,
        UserSecurityDetails currentUserSecurityInputApiVo
    ) {
        if (errors.hasErrors()) {
            return "taco-shop/client-order-form";
        }

        LOG.info("Order submitted: <{}>", tacoOrder);

        receiveCommand.execute(
            clientOrderInputMapper.map(
                tacoOrder, tacos.stream().map(tacoInputMapper::map).toList()
            ),
            userApiIdMapper.map(currentUserSecurityInputApiVo.userSecurity().id())
        );

        sessionStatus.setComplete();

        return "redirect:" + TacoUriConstant.BASE_URL;
    }

    @Override
    public String saveOrder(
        Model model,
        ClientOrderInputUiVo tacoOrder
    ) {
        model.addAttribute(TACO_ORDER_SESSION, tacoOrder);

        return "redirect:" + TacoUriConstant.Design.DESIGN_BASE_URL;
    }
}
