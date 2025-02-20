package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;

import java.util.Collection;
import java.util.Date;

public interface TacoBuilder<T extends Taco> {

    TacoBuilder<T> createdAt(Date createdAt);

    TacoBuilder<T> name(String name);

    <I extends Ingredient> TacoBuilder<T> ingredients(Collection<I> ingredients);

    T build();
}
