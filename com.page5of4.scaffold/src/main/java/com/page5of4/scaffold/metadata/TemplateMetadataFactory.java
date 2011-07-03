package com.page5of4.scaffold.metadata;

import static org.jvnet.inflector.Noun.pluralOf;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.ReflectionUtils;
import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class TemplateMetadataFactory {

   private final MetadataResolver metadataResolver;
   private final Repository repository;
   private final ConversionService conversionService;

   @Autowired
   public TemplateMetadataFactory(MetadataResolver metadataResolver, Repository repository, ConversionService conversionService) {
      super();
      this.metadataResolver = metadataResolver;
      this.repository = repository;
      this.conversionService = conversionService;
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, Class<?> objectClass, List<?> targetCollection, String propertyName, ScaffoldViewModel scaffoldViewModel) {
      if(propertyName != null) {
         return createInstancePropertyMetadata(targetObject, propertyName, scaffoldViewModel);
      }
      if(targetObject != null) {
         return createTemplateMetadata(targetObject, scaffoldViewModel);
      }
      return createCollectionTemplateMetadata(objectClass, targetCollection, scaffoldViewModel);
   }

   public TemplateMetadata createTemplateMetadata(Object targetObject, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         return new InstanceMetadata(classMetadata, targetObject, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, targetObject));
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createInstanceTemplateMetadata(Object targetObject, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         return new InstanceMetadata(classMetadata, targetObject, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, targetObject));
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createInstancePropertyMetadata(Object targetObject, String propertyName, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
         PropertyMetadata property = classMetadata.findProperty(propertyName);
         OneToManyPropertyMetadata oneToMany = createOneToMany(classMetadata.getObjectClass(), property);
         ManyToOnePropertyMetadata manyToOne = createManyToOne(classMetadata.getObjectClass(), property);
         return new InstancePropertyMetadata(classMetadata, scaffoldViewModel, createUrlsViewModel(scaffoldViewModel, targetObject), targetObject, property, oneToMany, manyToOne);
      }
      catch(IntrospectionException e) {
         throw new RuntimeException("Error creating template metadata", e);
      }
   }

   public TemplateMetadata createCollectionTemplateMetadata(Class<?> objectClass, List<?> targetCollection, ScaffoldViewModel scaffoldViewModel) {
      try {
         ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
         List<TemplateMetadata> targetCollectionMetas = new ArrayList<TemplateMetadata>();
         for(Object item : targetCollection) {
            targetCollectionMetas.add(createTemplateMetadata(item, scaffoldViewModel));
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

   public ManyToOnePropertyMetadata createManyToOne(Class<?> objectClass, PropertyMetadata property) {
      PropertyDescriptor descriptor = property.getPropertyDescriptor();
      Class<? extends Object> type = property.getPropertyType();
      NotNull notNullAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(NotNull.class, objectClass, descriptor);
      boolean nullable = notNullAnnotation == null;
      if(type.isEnum()) {
         return new ManyToOnePropertyMetadata(descriptor, type.getEnumConstants(), nullable);
      }
      ManyToOne manyToOneAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(ManyToOne.class, objectClass, descriptor);
      if(manyToOneAnnotation == null) {
         return null;
      }
      ScaffoldCollection collectionAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(ScaffoldCollection.class, objectClass, descriptor);
      Collection<?> found = repository.findAll(type);
      if(found != null) {
         List<Object> items = new ArrayList<Object>();
         items.addAll(found);
         if(collectionAnnotation == null) {
            return new ManyToOnePropertyMetadata(descriptor, items.toArray(), nullable);
         }
         return new ManyToOnePropertyMetadata(descriptor, items.toArray(), nullable, collectionAnnotation.label(), collectionAnnotation.value());
      }
      return null;
   }

   private static boolean shouldIncludeEmpty() {
      return true;
   }

   public OneToManyPropertyMetadata createOneToMany(Class<?> objectClass, PropertyMetadata property) {
      return null;
   }

}
