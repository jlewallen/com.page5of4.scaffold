package com.page5of4.scaffold.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.scaffold.ScaffoldTemplate;
import com.page5of4.scaffold.StringUtils;

public class ClassMetadata extends AbstractMetadata {
   private static final Logger logger = LoggerFactory.getLogger(ClassMetadata.class);

   private final Class<? extends Object> objectClass;
   private final List<PropertyMetadata> properties;

   public Class<? extends Object> getObjectClass() {
      return objectClass;
   }

   public List<PropertyMetadata> getProperties() {
      return properties;
   }

   public String getName() {
      return ClassUtils.getShortClassName(objectClass);
   }

   @JsonIgnore
   public String getCssClassName() {
      return StringUtils.lowercaseFirstLetter(ClassUtils.getShortClassName(objectClass));
   }

   @Override
   public String[] getCandidateTemplateNames() {
      List<String> names = new ArrayList<String>();
      ScaffoldTemplate annotation = objectClass.getAnnotation(ScaffoldTemplate.class);
      if(annotation != null) {
         names.add(annotation.value());
      }

      Class<? extends Object> klass = this.objectClass;
      while(klass != null) {
         names.add(ClassUtils.getShortClassName(klass));
         klass = klass.getSuperclass();
      }
      return names.toArray(new String[0]);
   }

   public ClassMetadata(Class<? extends Object> klass, Collection<PropertyMetadata> properties) {
      super();
      this.objectClass = klass;
      this.properties = new ArrayList<PropertyMetadata>(properties);
   }

   public PropertyMetadata findProperty(String propertyName) {
      for(PropertyMetadata property : properties) {
         if(property.getName().equals(propertyName)) {
            return property;
         }
      }
      throw new RuntimeException(String.format("Unable to locate property %s on %s", propertyName, objectClass));
   }
}
