package com.ss.scaffold;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

public class PropertyMetadata extends AbstractMetadata {
   private static final String HIDDEN_TEMPLATE_NAME = "Hidden";
   private Object target;
   private PropertyDescriptor descriptor;
   private boolean hidden;

   public Object getTarget() {
      return target;
   }

   public PropertyDescriptor getDescriptor() {
      return descriptor;
   }

   public boolean isHidden() {
      return hidden;
   }

   public boolean isVisible() {
      return !isHidden();
   }

   public String getName() {
      return descriptor.getName();
   }

   public String getDisplayName() {
      return StringUtils.humanize(descriptor.getDisplayName());
   }

   public String getPropertyNameTemplateName() {
      return StringUtils.capitaliseFirstLetter(getName());
   }

   public String getPropertyTypeTemplateName() {
      if(getPropertyType().isEnum()) {
         return "Enum";
      }
      return ClassUtils.getShortClassName(getPropertyType());
   }

   public Object getValue() {
      try {
         return descriptor.getReadMethod().invoke(target);
      }
      catch(Exception e) {
         throw new RuntimeException(String.format("Error reading %s from %s", getName(), target), e);
      }
   }

   public Object getDisplayValue() {
      return getValue();
   }

   @Override
   public String[] getCandidateTemplateNames() {
      List<String> names = new ArrayList<String>();
      ScaffoldTemplate annotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldTemplate.class, target.getClass(), descriptor);
      if(annotation != null) {
         names.add(annotation.value());
      }
      names.add(getPropertyNameTemplateName());
      if(hidden) {
         names.add(HIDDEN_TEMPLATE_NAME);
      }
      else {
         names.add(getPropertyTypeTemplateName());
      }
      return names.toArray(new String[0]);
   }

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
   }

   public PropertyMetadata(Object target, PropertyDescriptor descriptor) {
      super();
      this.target = target;
      this.descriptor = descriptor;
      this.hidden = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldHidden.class, target.getClass(), descriptor) != null;
   }
}
