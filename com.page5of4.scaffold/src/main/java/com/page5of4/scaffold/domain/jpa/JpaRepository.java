package com.page5of4.scaffold.domain.jpa;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.page5of4.scaffold.domain.Finders;
import com.page5of4.scaffold.domain.Repository;

public class JpaRepository implements Repository {

   @PersistenceContext
   private EntityManager entityManager;

   public EntityManager getEntityManager() {
      return entityManager;
   }

   @Override
   public Object findById(Class<?> entityClass, Object id) {
      if(id instanceof String) {
         if(id.toString().length() == 0) {
            return null;
         }
         id = Long.parseLong(id.toString());
      }
      return entityManager.find(entityClass, id);
   }

   @Override
   public List<?> findAll(Class<?> entityClass) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<?> query = cb.createQuery(entityClass);
      Root<?> from = query.from(entityClass);
      return entityManager.createQuery(query).getResultList();
   }

   @Override
   public List<?> findAll(Class<?> entityClass, int page) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<?> query = cb.createQuery(entityClass);
      Root<?> from = query.from(entityClass);
      return entityManager.createQuery(query).getResultList();
   }

   @Override
   public long countAll(Class<?> entityClass) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Long> cq = cb.createQuery(Long.class);
      CriteriaQuery<Long> query = cq.select(cb.count(cq.from(entityClass)));
      return entityManager.createQuery(query).getSingleResult();
   }

   @Override
   public Object getIdOf(Object entity) {
      try {
         Method idGetter = Finders.findIdGetter(entity.getClass());
         return idGetter.invoke(entity);
      }
      catch(Exception error) {
         throw new RuntimeException("Error getting Id: " + entity, error);
      }
   }

}
