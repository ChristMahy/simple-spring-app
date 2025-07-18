package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider;

public interface CassandraTacoProxyFactoryProvider {

    <T, F> F resolve(Class<T> entityClass);

}
