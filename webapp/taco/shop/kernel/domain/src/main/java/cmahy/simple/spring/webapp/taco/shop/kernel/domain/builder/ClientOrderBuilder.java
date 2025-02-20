package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

import java.util.List;

public interface ClientOrderBuilder<CO extends ClientOrder> {

    ClientOrderBuilder<CO> user(User user);

    ClientOrderBuilder<CO> deliveryName(String deliveryName);
    ClientOrderBuilder<CO> deliveryStreet(String deliveryStreet);
    ClientOrderBuilder<CO> deliveryCity(String deliveryCity);
    ClientOrderBuilder<CO> deliveryState(String deliveryState);
    ClientOrderBuilder<CO> deliveryZip(String deliveryZip);

    ClientOrderBuilder<CO> ccNumber(String ccNumber);
    ClientOrderBuilder<CO> ccExpiration(String ccExpiration);
    ClientOrderBuilder<CO> ccCVV(String ccCVV);

    <T extends Taco> ClientOrderBuilder<CO> tacos(List<T> tacos);

    CO build();
}
