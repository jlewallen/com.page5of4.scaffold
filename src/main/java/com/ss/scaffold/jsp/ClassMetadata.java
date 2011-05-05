package com.ss.scaffold.jsp;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassMetadata extends AbstractMetadata {
   private static final Logger logger = LoggerFactory.getLogger(ClassMetadata.class);

   public static ClassMetadata create(Class<? extends Object> klass) throws IntrospectionException {
      List<PropertyMetadata> properties = new ArrayList<PropertyMetadata>();
      BeanInfo beanInfo = Introspector.getBeanInfo(klass);
      for(PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
         properties.add(new PropertyMetadata(descriptor));
      }
      return new ClassMetadata(klass, properties);
   }

   private Class<? extends Object> klass;
   private List<PropertyMetadata> properties;

   public List<PropertyMetadata> getProperties() {
      return properties;
   }

   @Override
   public String[] getCandidateTemplateNames() {
      List<String> names = new ArrayList<String>();
      Class<? extends Object> klass = this.klass;
      while(klass != null) {
         names.add(ClassUtils.getShortClassName(klass));
         klass = klass.getSuperclass();
      }
      return names.toArray(new String[0]);
   }

   public ClassMetadata(Class<? extends Object> klass, Collection<PropertyMetadata> properties) {
      super();
      this.klass = klass;
      this.properties = new ArrayList<PropertyMetadata>(properties);
   }

   public PropertyMetadata findProperty(String propertyName) {
      for(PropertyMetadata property : properties) {
         if(property.getName().equals(propertyName)) {
            return property;
         }
      }
      throw new RuntimeException(String.format("Unable to locate property %s on %s", propertyName, klass));
   }
}
