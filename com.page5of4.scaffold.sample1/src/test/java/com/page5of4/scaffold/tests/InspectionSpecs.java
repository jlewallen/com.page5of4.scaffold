package com.page5of4.scaffold.tests;

import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.metamodel.ManagedType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = TestConfiguration.APPLICATION_CONTEXT)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class InspectionSpecs {

   @PersistenceUnit
   EntityManagerFactory emf;

   @Test
   @Transactional
   public void when_inspecting() {
      Set<ManagedType<?>> managedTypes = emf.getMetamodel().getManagedTypes();
      for(ManagedType<?> managedType : managedTypes) {
         System.out.println(managedType.getJavaType());
      }
   }
}
