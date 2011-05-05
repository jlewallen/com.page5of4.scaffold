package com.ss.scaffold;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

public class PropertyMetadata extends AbstractMetadata {
   private Object target;
   private PropertyDescriptor descriptor;

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
      names.add(getPropertyNameTemplateName());
      names.add(getPropertyTypeTemplateName());
      return names.toArray(new String[0]);
   }

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
   }

   public PropertyMetadata(Object target, PropertyDescriptor descriptor) {
      super();
      this.target = target;
      this.descriptor = descriptor;
   }
}
