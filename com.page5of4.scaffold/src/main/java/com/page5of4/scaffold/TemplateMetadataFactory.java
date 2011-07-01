package com.page5of4.scaffold;

import java.beans.IntrospectionException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class TemplateMetadataFactory {

   private MetadataResolver metadataResolver;

   @Autowired
   public TemplateMetadataFactory(MetadataResolver metadataResolver) {
      super();
      this.metadataResolver = metadataResolver;
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         return new TemplateMetadata(classMetadata, classMetadata, targetObject, scaffoldViewModel);
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createTemplateMetadata(Class<?> objectClass, List<?> targetCollection, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
         return new TemplateMetadata(classMetadata, classMetadata, targetCollection, scaffoldViewModel);
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

}
