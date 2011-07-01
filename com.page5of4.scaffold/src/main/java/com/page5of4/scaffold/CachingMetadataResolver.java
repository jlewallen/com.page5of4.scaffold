package com.page5of4.scaffold;

import java.beans.IntrospectionException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class CachingMetadataResolver implements MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(CachingMetadataResolver.class);

   private final ConversionService conversionService;
   private final Map<Class<?>, ClassMetadata> cache = new ConcurrentHashMap<Class<?>, ClassMetadata>();

   @Autowired
   public CachingMetadataResolver(ConversionService conversionService) {
      super();
      this.conversionService = conversionService;
   }

   public ClassMetadata resolve(Class<?> objectClass) throws IntrospectionException {
      if(objectClass == null) throw new IllegalArgumentException("Cannot resolve NULL object class.");
      ClassMetadata metadata = cache.get(objectClass);
      if(metadata == null) {
         logger.debug("Resolving {}", objectClass);
         metadata = ClassMetadata.create(conversionService, objectClass);
         cache.put(objectClass, metadata);
      }
      return metadata;
   }

}
