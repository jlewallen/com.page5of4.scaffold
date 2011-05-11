package com.ss.scaffold;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ClassUtils;

public class ManyToOnePropertyMetadata extends AssociationMetadata {

   private static final Logger logger = LoggerFactory.getLogger(ManyToOnePropertyMetadata.class);

   private Object[] items;

   public Object[] getItems() {
      return items;
   }

   public ManyToOnePropertyMetadata(PropertyMetadata property, Object[] items) {
      super(property, property.getPropertyType());
      this.items = items;
   }

   public static ManyToOnePropertyMetadata create(ConversionService conversionService, PropertyMetadata property) {
      ManyToOnePropertyMetadata metadata = tryCreate(conversionService, property);
      if(metadata != null) {
         return metadata;
      }
      throw new RuntimeException("Invalid ManyToOne property type: " + property);
   }

   public static ManyToOnePropertyMetadata tryCreate(ConversionService conversionService, PropertyMetadata property) {
      Class<? extends Object> type = property.getPropertyType();
      if(type.isEnum()) {
         return new ManyToOnePropertyMetadata(property, type.getEnumConstants());
      }
      Method finder = getFindAllFinder(type);
      if(finder != null) {
         try {
            Collection<?> found = (Collection<?>)finder.invoke(null, new Object[0]);
            List<LabelAndValue> items = new ArrayList<LabelAndValue>();
            if(conversionService.canConvert(type, LabelAndValue.class)) {
               for(Object value : found) {
                  items.add(conversionService.convert(value, LabelAndValue.class));
               }
            }
            else if(LabelAndValue.class.isAssignableFrom(type)) {
               for(Object value : found) {
                  items.add((LabelAndValue)value);
               }
            }
            else {
               for(Object value : found) {
                  items.add(new FallbackLabelAndValue(value));
               }
            }
            return new ManyToOnePropertyMetadata(property, items.toArray(new LabelAndValue[0]));
         }
         catch(Exception e) {
            logger.error("Error invoking finder: " + finder, e);
         }
      }
      return null;
   }

   private static Method getFindAllFinder(Class<?> entityClass) {
      String finderMethod = "findAll";
      Method[] methods = entityClass.getDeclaredMethods();
      for(Method method : methods) {
         if(Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 0 && method.getReturnType().equals(Collection.class)) {
            if(method.getName().equals(finderMethod)) {
               return method;
            }
         }
      }
      return null;
   }

   private static String getEntityName(Class<?> entityClass) {
      String shortName = ClassUtils.getShortName(entityClass);
      int lastDot = shortName.lastIndexOf('.');
      if(lastDot != -1) {
         return shortName.substring(lastDot + 1);
      }
      else {
         return shortName;
      }
   }

}
