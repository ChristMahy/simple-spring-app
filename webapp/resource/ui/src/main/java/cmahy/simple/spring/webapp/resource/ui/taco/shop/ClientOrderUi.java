package cmahy.simple.spring.webapp.resource.ui.taco.shop;

import cmahy.simple.spring.webapp.resource.ui.taco.TacoUriConstant.ClientOrder;
import cmahy.simple.spring.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@RequestMapping(path = ClientOrder.CLIENT_ORDER_BASE_URL)
@SessionAttributes(names = { ClientOrderUi.TACO_ORDER_SESSION, ClientOrderUi.TACOS })
public interface ClientOrderUi {

    String TACO_ORDER_SESSION = "tacoOrderSession";
    String TACO_ORDER = "tacoOrder";
    String TACOS = "tacos";

    @GetMapping
    String current(Model model);

    @PostMapping(params = "action=process-order")
    String saveOrder(
        Model model,
        @Valid @ModelAttribute(name = TACO_ORDER) ClientOrderInputVo tacoOrder,
        Errors errors,
        @ModelAttribute(name = TACOS) List<TacoInputVo> tacos,
        SessionStatus sessionStatus,
        @AuthenticationPrincipal(errorOnInvalidType = true) UserSecurityDetails currentUser
    ) throws Exception;
    // TODO: Better exception as signature... (More specific ???)

    @PostMapping(params = "action=another-taco")
    String saveOrder(
        Model model,
        @ModelAttribute(name = TACO_ORDER) ClientOrderInputVo tacoOrder
    );
}
