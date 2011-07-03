package com.page5of4.scaffold.configuration;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaConfigurer implements Configurer {

   private static final Logger logger = LoggerFactory.getLogger(JpaConfigurer.class);

   @PersistenceUnit
   private EntityManagerFactory entityManagerFactory;

   @Override
   @PostConstruct
   public void configure() {
      Metamodel metaModel = entityManagerFactory.getMetamodel();
      Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
      for(ManagedType<?> managedType : managedTypes) {
         logger.info("Type: {}", managedType.getJavaType());
      }
   }

}
