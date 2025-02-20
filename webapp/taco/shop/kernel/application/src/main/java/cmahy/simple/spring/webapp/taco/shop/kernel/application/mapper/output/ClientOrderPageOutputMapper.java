package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.ClientOrderPageOutputVo;
import jakarta.inject.Named;

import java.util.*;

@Named
public class ClientOrderPageOutputMapper {

    private final ClientOrderOutputMapper clientOrderOutputMapper;

    public ClientOrderPageOutputMapper(ClientOrderOutputMapper clientOrderOutputMapper) {
        this.clientOrderOutputMapper = clientOrderOutputMapper;
    }

    public ClientOrderPageOutputVo map(ClientOrderPage<ClientOrder> source) throws RequiredException {
        if (Objects.isNull(source)) {
            throw new RequiredException(ClientOrderPage.class);
        }

        ArrayList<ClientOrderOutputVo> clientOrdersVo = new ArrayList<>(source.content().size());

        for (ClientOrder clientOrder : source.content()) {
            clientOrdersVo.add(clientOrderOutputMapper.map(clientOrder));
        }

        return new ClientOrderPageOutputVo(
            Collections.unmodifiableList(clientOrdersVo),
            source.totalElements()
        );
    }
}
