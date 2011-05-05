package com.ss.scaffold.jsp;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassMetadata extends AbstractMetadata {
   private static final Logger logger = LoggerFactory.getLogger(ClassMetadata.class);

   public static ClassMetadata create(Class<? extends Object> klass, Object object) throws IntrospectionException {
      List<PropertyMetadata> properties = new ArrayList<PropertyMetadata>();
      BeanInfo beanInfo = Introspector.getBeanInfo(klass);
      for(PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
         if(!shouldSkip(descriptor)) {
            properties.add(new PropertyMetadata(descriptor));
         }
      }
      Collections.sort(properties, new Comparator<PropertyMetadata>() {
         @Override
         public int compare(PropertyMetadata o1, PropertyMetadata o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return new ClassMetadata(klass, properties, object);
   }

   public static boolean shouldSkip(PropertyDescriptor descriptor) {
      String[] skip = new String[] { "class" };
      return ArrayUtils.contains(skip, descriptor.getName());
   }

   private Class<? extends Object> klass;
   private List<PropertyMetadata> properties;
   private Object object;

   public List<PropertyMetadata> getProperties() {
      return properties;
   }

   public Class<? extends Object> getObjectClass() {
      return klass;
   }

   public Object getObject() {
      return object;
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

   public ClassMetadata(Class<? extends Object> klass, Collection<PropertyMetadata> properties, Object object) {
      super();
      this.klass = klass;
      this.object = object;
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
