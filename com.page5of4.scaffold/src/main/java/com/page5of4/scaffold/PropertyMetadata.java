package com.page5of4.scaffold;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.ConvertingPropertyEditorAdapter;

import com.page5of4.scaffold.spring.ValueFormatter;

public class PropertyMetadata extends AbstractMetadata {
   private static final String HIDDEN_TEMPLATE_NAME = "Hidden";
   private String formName;
   private Object target;
   private PropertyDescriptor descriptor;
   private boolean hidden;
   private String help;
   private ConversionService conversionService;

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
      BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(target);
      Object value = bw.getPropertyValue(getName());
      TypeDescriptor td = bw.getPropertyTypeDescriptor(getName());
      if(!conversionService.canConvert(TypeDescriptor.valueOf(String.class), td)) {
         return value;
      }
      ConvertingPropertyEditorAdapter editor = new ConvertingPropertyEditorAdapter(conversionService, td);
      return ValueFormatter.getDisplayString(value, editor, true);
   }

   public Object getConvertedStringValue() {
      return conversionService.convert(getPropertyType(), String.class);
   }

   private OneToManyPropertyMetadata oneToManyMetadata;

   public boolean getIsOneToMany() {
      if(oneToManyMetadata == null) {
         oneToManyMetadata = OneToManyPropertyMetadata.tryCreate(this);
      }
      return oneToManyMetadata != null;
   }

   public OneToManyPropertyMetadata getOneToMany() {
      if(oneToManyMetadata == null) {
         oneToManyMetadata = OneToManyPropertyMetadata.create(this);
      }
      return oneToManyMetadata;
   }

   private ManyToOnePropertyMetadata manyToOneMetadata;

   public boolean getIsManyToOne() {
      if(manyToOneMetadata == null) {
         manyToOneMetadata = ManyToOnePropertyMetadata.tryCreate(conversionService, this);
      }
      return manyToOneMetadata != null;
   }

   public ManyToOnePropertyMetadata getManyToOne() {
      if(manyToOneMetadata == null) {
         manyToOneMetadata = ManyToOnePropertyMetadata.create(conversionService, this);
      }
      return manyToOneMetadata;
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
         ScaffoldTemplate annotation = ReflectionUtils.getFirstAnnotationOnAnnotationsIn(ScaffoldTemplate.class, ReflectionUtils.getFieldOrMethodAnnotations(target.getClass(), descriptor));
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

   public Class<? extends Object> getPropertyType() {
      return descriptor.getPropertyType();
   }

   public PropertyMetadata(ConversionService conversionService, String formPrefix, PropertyDescriptor descriptor, Object target) {
      super();
      this.conversionService = conversionService;
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
