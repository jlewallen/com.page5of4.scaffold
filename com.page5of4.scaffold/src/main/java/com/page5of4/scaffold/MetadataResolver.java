package com.page5of4.scaffold;

import java.beans.IntrospectionException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(MetadataResolver.class);

   private ConversionService conversionService;

   @Autowired
   public MetadataResolver(ConversionService conversionService) {
      super();
      this.conversionService = conversionService;
   }

   public ClassMetadata resolve(Object targetObject, String formPrefix) throws IntrospectionException {
      if(targetObject == null) throw new IllegalArgumentException("Cannot resolve NULL object.");
      Class<? extends Object> objectClass = targetObject.getClass();
      return ClassMetadata.create(conversionService, formPrefix, objectClass, targetObject);
   }

   public ClassMetadata resolve(Class<?> objectClass, List<?> targetObjects, String formPrefix) throws IntrospectionException {
      if(targetObjects == null) throw new IllegalArgumentException("Cannot resolve NULL object.");
      if(objectClass == null) throw new IllegalArgumentException("Cannot resolve NULL object class.");
      return ClassMetadata.create(conversionService, formPrefix, objectClass, targetObjects);
   }

}
