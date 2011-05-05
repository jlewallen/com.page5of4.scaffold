package com.ss.scaffold.jsp;

import java.beans.IntrospectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(MetadataResolver.class);

   public AbstractMetadata resolve(Object target, String propertyName) throws IntrospectionException {
      Class<? extends Object> klass = target.getClass();
      ClassMetadata meta = ClassMetadata.create(klass);

      logger.info(String.format("%s %s", klass.getName(), StringUtils.join(meta.getCandidateTemplateNames(), ", ")));
      for(PropertyMetadata property : meta.getProperties()) {
         logger.info(String.format("  %s %s %s", property.getDisplayName(), property.getName(), StringUtils.join(property.getCandidateTemplateNames(), ", ")));
      }

      if(propertyName == null) {
         return meta;
      }

      return meta.findProperty(propertyName);
   }

}
