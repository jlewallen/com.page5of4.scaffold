package com.page5of4.scaffold.metadata;

import static org.jvnet.inflector.Noun.pluralOf;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class TemplateMetadataFactory {

   private final MetadataResolver metadataResolver;
   private final Repository repository;

   @Autowired
   public TemplateMetadataFactory(MetadataResolver metadataResolver, Repository repository) {
      super();
      this.metadataResolver = metadataResolver;
      this.repository = repository;
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, Class<?> objectClass, List<?> targetCollection, AbstractMetadata currentMetadata, ScaffoldViewModel scaffoldViewModel) {
      if(targetObject != null) {
         return createTemplateMetadata(targetObject, currentMetadata, scaffoldViewModel);
      }
      return createTemplateMetadata(objectClass, targetCollection, scaffoldViewModel);
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, AbstractMetadata currentMetadata, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         if(currentMetadata == null) currentMetadata = classMetadata;
         return new InstanceMetadata(classMetadata, currentMetadata, targetObject, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, targetObject));
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         return new InstanceMetadata(classMetadata, classMetadata, targetObject, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, targetObject));
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createTemplateMetadata(Class<?> objectClass, List<?> targetCollection, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
         List<TemplateMetadata> targetCollectionMetas = new ArrayList<TemplateMetadata>();
         for(Object item : targetCollection) {
            targetCollectionMetas.add(createTemplateMetadata(item, classMetadata, scaffoldViewModel));
         }
         return new CollectionMetadata(classMetadata, targetCollection, targetCollectionMetas, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, objectClass));
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public ScaffoldViewModel createScaffoldViewModel(Class<?> objectClass) {
      String resourceName = StringUtils.capitaliseFirstLetter(objectClass.getSimpleName());
      String collectionName = pluralOf(resourceName);
      return new ScaffoldViewModel(resourceName, collectionName);
   }

   private UrlsViewModel createUrlsViewModel(ScaffoldViewModel scaffoldViewModel, Class<?> objectClass) {
      String collectionName = scaffoldViewModel.getCollectionName().toLowerCase();
      String indexUrl = String.format("/%s", collectionName);
      String createUrl = String.format("/%s", collectionName);
      String createFormUrl = String.format("/%s/form", collectionName);
      return new UrlsViewModel(indexUrl, createUrl, createFormUrl);
   }

   private UrlsViewModel createUrlsViewModel(ScaffoldViewModel scaffoldViewModel, Object targetObject) {
      String collectionName = scaffoldViewModel.getCollectionName().toLowerCase();
      String indexUrl = String.format("/%s", collectionName);
      String createUrl = String.format("/%s", collectionName);
      String createFormUrl = String.format("/%s/form", collectionName);
      String showUrl = String.format("/%s/%s", collectionName, getId(targetObject));
      String updateUrl = String.format("/%s/%s", collectionName, getId(targetObject));
      String updateFormUrl = String.format("/%s/%s/form", collectionName, getId(targetObject));
      String deleteUrl = String.format("/%s/%s", collectionName, getId(targetObject));
      return new UrlsViewModel(indexUrl, createUrl, createFormUrl, showUrl, updateUrl, updateFormUrl, deleteUrl);
   }

   private Object getId(Object object) {
      return repository.getIdOf(object);
   }

}
