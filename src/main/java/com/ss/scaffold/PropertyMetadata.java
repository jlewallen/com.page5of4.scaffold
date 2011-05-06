package com.ss.scaffold;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

public class PropertyMetadata extends AbstractMetadata {
   private static final String HIDDEN_TEMPLATE_NAME = "Hidden";
   private String formName;
   private Object target;
   private PropertyDescriptor descriptor;
   private boolean hidden;
   private String help;

   public boolean isHidden() {
      return hidden;
   }

   public boolean isVisible() {
      return !isHidden();
   }

   public String getName() {
      return descriptor.getName();
   }

   public String getFormName() {
      return this.formName;
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

   public String getHelp() {
      return this.help;
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

   private MultivaluedPropertyMetadata multivaluedPropertyMetadata;

   public MultivaluedPropertyMetadata getMultivaluedPropertyMetadata() {
      if(multivaluedPropertyMetadata == null) {
         multivaluedPropertyMetadata = MultivaluedPropertyMetadata.create(this);
      }
      return multivaluedPropertyMetadata;
   }

   @Override
   public String[] getCandidateTemplateNames() {
      List<String> names = new ArrayList<String>();
      {
         ScaffoldTemplate annotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldTemplate.class, target.getClass(), descriptor);
         if(annotation != null) {
            names.add(annotation.value());
         }
      }
      {
         ScaffoldTextArea annotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldTextArea.class, target.getClass(), descriptor);
         if(annotation != null) {
            names.add("TextArea");
         }
      }
      names.add(getPropertyNameTemplateName());
      if(hidden) {
         names.add(HIDDEN_TEMPLATE_NAME);
      }
      else {
         names.add(getPropertyTypeTemplateName());
      }
      if(getPropertyType().isEnum()) {
         names.add("Enum");
      }
      return names.toArray(new String[0]);
   }

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
   }

   public PropertyMetadata(String formPrefix, PropertyDescriptor descriptor, Object target) {
      super();
      this.formName = formPrefix + "." + descriptor.getName();
      this.target = target;
      this.descriptor = descriptor;
      this.hidden = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldHidden.class, target.getClass(), descriptor) != null;
      ScaffoldHelp helpAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldHelp.class, target.getClass(), descriptor);
      if(helpAnnotation != null) {
         this.help = helpAnnotation.value();
      }
   }
}
