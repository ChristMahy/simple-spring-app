package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

public interface Loader {

   <E> Class<E> entityClass();

}
