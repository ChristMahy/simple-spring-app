package cmahy.webapp.resource.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant.ClientOrder;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputApiVo;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@RequestMapping(path = ClientOrder.CLIENT_ORDER_BASE_URL)
@SessionAttributes(names = { ClientOrderApi.TACO_ORDER_SESSION, ClientOrderApi.TACOS })
public interface ClientOrderApi {

    String TACO_ORDER_SESSION = "tacoOrderSession";
    String TACO_ORDER = "tacoOrder";
    String TACOS = "tacos";

    @GetMapping
    String current(Model model);

    @PostMapping(params = "action=process-order")
    String saveOrder(
        Model model,
        @Valid @ModelAttribute(name = TACO_ORDER) ClientOrderInputApiVo tacoOrder,
        Errors errors,
        @ModelAttribute(name = TACOS) List<TacoInputApiVo> tacos,
        SessionStatus sessionStatus
    );

    @PostMapping(params = "action=another-taco")
    String saveOrder(
        Model model,
        @ModelAttribute(name = TACO_ORDER) ClientOrderInputApiVo tacoOrder
    );
}
