package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.Loader;

public interface TacoLoaderProvider {

    <T, L extends Loader> L resolve(Class<T> entityClass);

}
