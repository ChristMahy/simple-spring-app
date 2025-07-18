package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.Loader;

public interface UserLoaderProvider {

    <T, L extends Loader> L resolve(Class<T> entityClass);

}
