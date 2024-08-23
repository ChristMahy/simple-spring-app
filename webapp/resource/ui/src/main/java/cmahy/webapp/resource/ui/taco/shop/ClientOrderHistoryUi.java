package cmahy.webapp.resource.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.vo.output.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL + TacoUriConstant.ClientOrder.CLIENT_ORDER_HISTORY_URL)
public interface ClientOrderHistoryUi {

    @GetMapping
    String orders(
        @RequestParam(name = "page-size", required = false) Optional<Integer> pageSize,
        @RequestParam(name = "page-number", required = false) Optional<Integer> pageNumber,
        Model model,
        @AuthenticationPrincipal(errorOnInvalidType = true) UserSecurityDetails userSecurityDetails
    ) throws Exception;
}
