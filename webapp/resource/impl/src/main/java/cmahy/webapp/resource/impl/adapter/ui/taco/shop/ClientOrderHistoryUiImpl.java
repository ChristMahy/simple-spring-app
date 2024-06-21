package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.common.entity.page.EntityPageableBuilder;
import cmahy.webapp.resource.impl.adapter.taco.shop.properties.OrderProperties;
import cmahy.webapp.resource.impl.adapter.user.mapper.id.UserApiIdMapper;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetAllClientOrderPagedQuery;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.taco.shop.vo.output.ClientOrderPageOutputVo;
import cmahy.webapp.resource.ui.taco.shop.ClientOrderHistoryUi;
import cmahy.webapp.resource.ui.vo.output.Pagination;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class ClientOrderHistoryUiImpl implements ClientOrderHistoryUi {

    private final GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;
    private final OrderProperties orderProperties;
    private final UserApiIdMapper userApiIdMapper;

    public ClientOrderHistoryUiImpl(
        GetAllClientOrderPagedQuery getAllClientOrderPagedQuery,
        OrderProperties orderProperties,
        UserApiIdMapper userApiIdMapper
    ) {
        this.getAllClientOrderPagedQuery = getAllClientOrderPagedQuery;
        this.orderProperties = orderProperties;
        this.userApiIdMapper = userApiIdMapper;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    @Transactional
    public String orders(
        Optional<Integer> pageSize,
        Optional<Integer> pageNumber,
        Model model,
        UserSecurityDetails userSecurityDetails
    ) {
        PageableInputAppVo pageable = EntityPageableBuilder.<PageableInputAppVo>instance(orderProperties.pageSize())
            .withPageSize(pageSize.orElse(null))
            .withPageNumber(pageNumber.orElse(null))
            .build(PageableInputAppVo.class);

        ClientOrderPageOutputVo orders =
            getAllClientOrderPagedQuery.execute(userApiIdMapper.map(userSecurityDetails.userSecurity().id()), pageable);

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
