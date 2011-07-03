package com.page5of4.scaffold.metadata;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.ConvertingPropertyEditorAdapter;

import com.page5of4.scaffold.ReflectionUtils;
import com.page5of4.scaffold.ScaffoldHelp;
import com.page5of4.scaffold.ScaffoldHidden;
import com.page5of4.scaffold.ScaffoldTemplate;
import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.spring.ValueFormatter;

public class PropertyMetadata extends AbstractMetadata {
   private static final String HIDDEN_TEMPLATE_NAME = "Hidden";
   private final PropertyDescriptor descriptor;
   private final boolean hidden;
   private final String help;
   private final ConversionService conversionService;
   private final Class<?> targetClass;

   public PropertyDescriptor getPropertyDescriptor() {
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

   public String getCssClassName() {
      return getName();
   }

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
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

   public Object getDisplayValue(Object object) {
      BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(object);
      Object value = bw.getPropertyValue(getName());
      TypeDescriptor td = bw.getPropertyTypeDescriptor(getName());
      if(!conversionService.canConvert(TypeDescriptor.valueOf(String.class), td)) {
         return value;
      }
      ConvertingPropertyEditorAdapter editor = new ConvertingPropertyEditorAdapter(conversionService, td);
      return ValueFormatter.getDisplayString(value, editor, true);
   }

   @Override
   public String[] getCandidateTemplateNames() {
      List<String> names = new ArrayList<String>();
      {
         ScaffoldTemplate annotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldTemplate.class, targetClass, descriptor);
         if(annotation != null) {
            names.add(annotation.value());
         }
      }
      {
         ScaffoldTemplate annotation = ReflectionUtils.getFirstAnnotationOnAnnotationsIn(ScaffoldTemplate.class, ReflectionUtils.getFieldOrMethodAnnotations(targetClass, descriptor));
         if(annotation != null) {
            names.add(annotation.value());
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
      if(!names.contains("String")) {
         names.add("String");
      }
      return names.toArray(new String[0]);
   }

   public PropertyMetadata(ConversionService conversionService, Class<?> targetClass, PropertyDescriptor descriptor) {
      super();
      this.conversionService = conversionService;
      this.targetClass = targetClass;
      this.descriptor = descriptor;
      this.hidden = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldHidden.class, targetClass, descriptor) != null;
      ScaffoldHelp helpAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldHelp.class, targetClass, descriptor);
      if(helpAnnotation != null) {
         this.help = helpAnnotation.value();
      }
      else {
         this.help = "";
      }
   }
}
