package com.page5of4.scaffold.sample1.web;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.page5of4.scaffold.sample1.web.SimpleEntity;

public aspect EntityAspect {
   
   @PersistenceContext
   transient EntityManager SimpleEntity.entityManager;

   public void SimpleEntity.persist() {
      
   }
   
   public void SimpleEntity.remove() {
      
   }
   
}
