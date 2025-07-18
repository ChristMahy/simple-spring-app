package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

public interface Loader {

    <E> Class<E> entityClass();
    
}
