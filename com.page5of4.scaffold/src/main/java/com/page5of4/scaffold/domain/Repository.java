package com.page5of4.scaffold.domain;

import java.util.List;

public interface Repository {

   Object findById(Class<?> entityClass, Object id);

   List<?> findAll(Class<?> entityClass);

   List<?> findAll(Class<?> entityClass, int page);

   long countAll(Class<?> entityClass);

   Object getIdOf(Object entity);

}
