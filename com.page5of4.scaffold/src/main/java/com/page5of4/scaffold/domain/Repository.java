package com.page5of4.scaffold.domain;

import java.util.Collection;

public interface Repository {

   Object findById(Class<?> entityClass, Object id);

   Collection<?> findAll(Class<?> entityClass);

   Object getIdOf(Object entity);

}
