package com.page5of4.scaffold.metadata;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.ReflectionUtils;
import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class TemplateMetadataFactory {

   private final MetadataResolver metadataResolver;
   private final Repository repository;
   private final ScaffoldViewModelFactory viewModelFactory;

   public MetadataResolver getMetadataResolver() {
      return metadataResolver;
   }

   public ScaffoldViewModelFactory getViewModelFactory() {
      return viewModelFactory;
   }

   @Autowired
   public TemplateMetadataFactory(MetadataResolver metadataResolver, Repository repository, ScaffoldViewModelFactory viewModelFactory) {
      super();
      this.metadataResolver = metadataResolver;
      this.repository = repository;
      this.viewModelFactory = viewModelFactory;
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
      ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
      return new InstanceMetadata(classMetadata, targetObject, scaffoldViewModel, viewModelFactory.createUrlsViewModel(scaffoldViewModel, targetObject));
   }

   public TemplateMetadata createInstanceTemplateMetadata(Object targetObject, ScaffoldViewModel scaffoldViewModel) {
      ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
      return new InstanceMetadata(classMetadata, targetObject, scaffoldViewModel, viewModelFactory.createUrlsViewModel(scaffoldViewModel, targetObject));
   }

   public TemplateMetadata createInstancePropertyMetadata(Object targetObject, String propertyName, ScaffoldViewModel scaffoldViewModel) {
      ClassMetadata classMetadata = metadataResolver.resolve(targetObject.getClass());
      PropertyMetadata property = classMetadata.findProperty(propertyName);
      OneToManyPropertyMetadata oneToMany = createOneToMany(classMetadata.getObjectClass(), property);
      ManyToOnePropertyMetadata manyToOne = createManyToOne(classMetadata.getObjectClass(), property);
      return new InstancePropertyMetadata(classMetadata, scaffoldViewModel, viewModelFactory.createUrlsViewModel(scaffoldViewModel, targetObject), targetObject, property, oneToMany, manyToOne);
   }

   public TemplateMetadata createCollectionTemplateMetadata(Class<?> objectClass, List<?> targetCollection, ScaffoldViewModel scaffoldViewModel) {
      ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
      List<TemplateMetadata> targetCollectionMetas = new ArrayList<TemplateMetadata>();
      for(Object item : targetCollection) {
         targetCollectionMetas.add(createTemplateMetadata(item, scaffoldViewModel));
      }
      return new CollectionMetadata(classMetadata, targetCollection, targetCollectionMetas, scaffoldViewModel, viewModelFactory.createUrlsViewModel(scaffoldViewModel, objectClass));
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

   public VisibleClassMetadata createVisibleMetadata(Class<?> objectClass) {
      ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
      ScaffoldViewModel viewModel = viewModelFactory.createScaffoldViewModel(objectClass);
      List<VisiblePropertyMetadata> properties = new ArrayList<VisiblePropertyMetadata>();
      for(PropertyMetadata pm : classMetadata.getProperties()) {
         properties.add(new VisiblePropertyMetadata(pm.getName(), pm.getPropertyType().getName()));
      }
      return new VisibleClassMetadata(classMetadata.getName(), viewModelFactory.createUrlsViewModel(viewModel, objectClass), properties);
   }

   public static class VisibleClassMetadata {
      private final String name;
      private final UrlsViewModel urls;
      private final List<VisiblePropertyMetadata> properties;

      public String getName() {
         return name;
      }

      public UrlsViewModel getUrls() {
         return urls;
      }

      public List<VisiblePropertyMetadata> getProperties() {
         return properties;
      }

      public VisibleClassMetadata(String name, UrlsViewModel urls, List<VisiblePropertyMetadata> properties) {
         super();
         this.name = name;
         this.urls = urls;
         this.properties = properties;
      }
   }

   public static class VisiblePropertyMetadata {
      private final String name;
      private final String typeName;

      public String getName() {
         return name;
      }

      public String getTypeName() {
         return typeName;
      }

      public VisiblePropertyMetadata(String name, String typeName) {
         super();
         this.name = name;
         this.typeName = typeName;
      }
   }

}
