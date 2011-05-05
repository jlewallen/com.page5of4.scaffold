package com.ss.scaffold.jsp;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

public class PropertyMetadata {
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
      return StringUtils.capitaliseFirstLetter(ClassUtils.getShortClassName(getPropertyType()));
   }

   public String[] getPossibleTemplateNames() {
      List<String> names = new ArrayList<String>();
      names.add(getPropertyNameTemplateName());
      names.add(getPropertyTypeTemplateName());
      return names.toArray(new String[0]);
   }

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
   }

   public PropertyMetadata(PropertyDescriptor descriptor) {
      super();
      this.descriptor = descriptor;
   }
}
