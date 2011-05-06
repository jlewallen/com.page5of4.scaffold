package com.ss.scaffold;

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

   public ClassMetadata resolve(ScaffoldModel model) throws IntrospectionException {
      Object target = model.getTargetObject();
      if(target == null) {
         throw new IntrospectionException("Cannot resolve NULL object.");
      }
      Class<? extends Object> klass = target.getClass();
      ClassMetadata meta = ClassMetadata.create(conversionService, klass, model.getFormPrefix(), target);

      logger.info(String.format("Resolving: %s %s %s", target, klass.getName(), StringUtils.join(meta.getCandidateTemplateNames(), ", ")));
      for(PropertyMetadata property : meta.getProperties()) {
         logger.info(String.format("  %s %s %s", property.getDisplayName(), property.getName(), StringUtils.join(property.getCandidateTemplateNames(), ", ")));
      }

      return meta;
   }

}
