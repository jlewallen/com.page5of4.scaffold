package com.page5of4.scaffold;

import java.beans.IntrospectionException;

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

   public ClassMetadata resolve(Class<?> objectClass) throws IntrospectionException {
      if(objectClass == null) throw new IllegalArgumentException("Cannot resolve NULL object class.");
      logger.debug("Resolving {}", objectClass);
      return ClassMetadata.create(conversionService, objectClass);
   }

}
