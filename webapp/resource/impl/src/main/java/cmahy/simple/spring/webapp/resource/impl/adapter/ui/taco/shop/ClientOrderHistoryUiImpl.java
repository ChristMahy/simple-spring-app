package cmahy.simple.spring.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.simple.spring.common.entity.page.DefaultEntityPageableImpl;
import cmahy.simple.spring.common.entity.page.EntityPageableBuilder;
import cmahy.simple.spring.webapp.resource.impl.adapter.taco.shop.properties.OrderProperties;
import cmahy.simple.spring.webapp.resource.ui.taco.shop.ClientOrderHistoryUi;
import cmahy.simple.spring.webapp.resource.ui.vo.output.Pagination;
import cmahy.simple.spring.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.query.GetAllClientOrderPagedQuery;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderPageOutputVo;
import cmahy.simple.spring.webapp.user.kernel.exception.UserNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class ClientOrderHistoryUiImpl implements ClientOrderHistoryUi {

    private final GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;
    private final OrderProperties orderProperties;

    public ClientOrderHistoryUiImpl(
        GetAllClientOrderPagedQuery getAllClientOrderPagedQuery,
        OrderProperties orderProperties
    ) {
        this.getAllClientOrderPagedQuery = getAllClientOrderPagedQuery;
        this.orderProperties = orderProperties;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    @Transactional
    public String orders(
        Optional<Integer> pageSize,
        Optional<Integer> pageNumber,
        Model model,
        UserSecurityDetails userSecurityDetails
    ) throws UserNotFoundException, RequiredException {
        DefaultEntityPageableImpl pageable = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(orderProperties.pageSize())
            .withPageSize(pageSize.orElse(null))
            .withPageNumber(pageNumber.orElse(null))
            .build(DefaultEntityPageableImpl.class);

        ClientOrderPageOutputVo orders =
            getAllClientOrderPagedQuery.execute(userSecurityDetails.userSecurity().id(), pageable);

        model.addAttribute("orders", orders);
        model.addAttribute(
            "pagination",
            new Pagination(
                IntStream.rangeClosed(1, (int) (orders.totalElements() / pageable.pageSize()))
                    .boxed()
                    .toList(),
                pageable.pageSize(),
                orders.totalElements()
            )
        );

        return "taco-shop/history-all";
    }
}
