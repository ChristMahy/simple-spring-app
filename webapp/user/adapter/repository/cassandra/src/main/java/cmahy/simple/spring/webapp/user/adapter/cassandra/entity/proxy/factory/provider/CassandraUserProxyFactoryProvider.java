package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider;

public interface CassandraUserProxyFactoryProvider {

    <T, F> F resolve(Class<T> entityClass);

}
