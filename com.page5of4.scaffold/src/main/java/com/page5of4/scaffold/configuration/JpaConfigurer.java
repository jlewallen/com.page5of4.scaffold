package com.page5of4.scaffold.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

   @PostConstruct
   public void configure() {
      Metamodel metaModel = entityManagerFactory.getMetamodel();
      Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
      for(ManagedType<?> managedType : managedTypes) {
         logger.info("Type: {}", managedType.getJavaType());
      }
   }

   @Override
   public Collection<Class<?>> findAllScaffoldClasses() {
      List<Class<?>> classes = new ArrayList<Class<?>>();
      Metamodel metaModel = entityManagerFactory.getMetamodel();
      Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
      for(ManagedType<?> managedType : managedTypes) {
         classes.add(managedType.getJavaType());
      }
      return classes;
   }

   @Override
   public String getUrlPrefix() {
      return "/admin";
   }

}
