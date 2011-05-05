package com.ss.scaffold.jsp;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassMetadata {
   private static final Logger logger = LoggerFactory.getLogger(ClassMetadata.class);
   private List<PropertyMetadata> properties;

   public List<PropertyMetadata> getProperties() {
      return properties;
   }

   public ClassMetadata(Collection<PropertyMetadata> properties) {
      super();
      this.properties = new ArrayList<PropertyMetadata>(properties);
   }

   public static ClassMetadata create(Class<? extends Object> klass) throws IntrospectionException {
      List<PropertyMetadata> properties = new ArrayList<PropertyMetadata>();
      BeanInfo beanInfo = Introspector.getBeanInfo(klass);
      for(PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
         properties.add(new PropertyMetadata(descriptor));
      }
      return new ClassMetadata(properties);
   }
}