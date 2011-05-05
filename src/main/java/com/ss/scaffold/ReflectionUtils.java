package com.ss.scaffold;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectionUtils {

   public static <T extends Annotation> T getFieldOrMethodAnnotation(Class<T> annotationClass, Class<? extends Object> klass, PropertyDescriptor descriptor) {
      try {
         T annotation = descriptor.getReadMethod().getAnnotation(annotationClass);
         if(annotation != null) {
            return annotation;
         }
         Field field = klass.getDeclaredField(descriptor.getName());
         if(field == null) return null;
         return field.getAnnotation(annotationClass);
      }
      catch(Exception error) {
         throw new RuntimeException("Error getting field", error);
      }
   }

}
