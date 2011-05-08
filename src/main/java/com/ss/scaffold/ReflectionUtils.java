package com.ss.scaffold;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

   public static Collection<Annotation> getFieldOrMethodAnnotations(Class<? extends Object> klass, PropertyDescriptor descriptor) {
      try {
         List<Annotation> annotations = new ArrayList<Annotation>();
         for(Annotation a : descriptor.getReadMethod().getAnnotations()) {
            annotations.add(a);
         }
         Field field = klass.getDeclaredField(descriptor.getName());
         if(field != null) {
            for(Annotation a : field.getAnnotations()) {
               annotations.add(a);
            }
         }
         return annotations;
      }
      catch(Exception error) {
         throw new RuntimeException("Error getting field", error);
      }
   }

   public static <T extends Annotation> T getFirstAnnotationOnAnnotationsIn(Class<T> annotationClass, Collection<Annotation> annotations) {
      try {
         for(Annotation a : annotations) {
            T annotationAnnotation = a.annotationType().getAnnotation(annotationClass);
            if(annotationAnnotation != null) {
               return annotationAnnotation;
            }
         }
         return null;
      }
      catch(Exception error) {
         throw new RuntimeException("Error getting field", error);
      }
   }

}
