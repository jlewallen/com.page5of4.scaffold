package com.ss.scaffold;

import java.beans.IntrospectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(MetadataResolver.class);

   public ClassMetadata resolve(Object target) throws IntrospectionException {
      if(target == null) {
         throw new IntrospectionException("Cannot resolve NULL object.");
      }
      Class<? extends Object> klass = target.getClass();
      ClassMetadata meta = ClassMetadata.create(klass, target);

      logger.info(String.format("Resolving: %s %s %s", target, klass.getName(), StringUtils.join(meta.getCandidateTemplateNames(), ", ")));
      for(PropertyMetadata property : meta.getProperties()) {
         logger.info(String.format("  %s %s %s", property.getDisplayName(), property.getName(), StringUtils.join(property.getCandidateTemplateNames(), ", ")));
      }

      return meta;
   }

}
