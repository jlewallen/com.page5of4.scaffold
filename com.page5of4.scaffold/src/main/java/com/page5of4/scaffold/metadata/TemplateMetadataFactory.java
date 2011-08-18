package com.page5of4.scaffold.metadata;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.ReflectionUtils;
import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.TypeUrlsViewModel;
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
         VisibleClassMetadata propertyClassMetadata = null;
         if(pm.getPropertyTypeHasMetadata()) {
            propertyClassMetadata = createVisibleMetadata(pm.getPropertyType());
         }
         properties.add(new VisiblePropertyMetadata(pm.getName(), pm.getPropertyType().getName(), propertyClassMetadata));
      }
      TypeUrlsViewModel urls = new TypeUrlsViewModel(viewModelFactory.createUrlsViewModel(viewModel, objectClass));
      return new VisibleClassMetadata(classMetadata.getName(), objectClass.getName(), urls, properties);
   }

   public static class VisibleClassMetadata {
      private final String name;
      private final String typeName;
      private final TypeUrlsViewModel urls;
      private final List<VisiblePropertyMetadata> properties;

      public String getName() {
         return name;
      }

      public String getTypeName() {
         return typeName;
      }

      public TypeUrlsViewModel getUrls() {
         return urls;
      }

      public List<VisiblePropertyMetadata> getProperties() {
         return properties;
      }

      public VisibleClassMetadata(String name, String typeName, TypeUrlsViewModel urls, List<VisiblePropertyMetadata> properties) {
         super();
         this.name = name;
         this.typeName = typeName;
         this.urls = urls;
         this.properties = properties;
      }
   }

   @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
   public static class VisiblePropertyMetadata {
      private final String name;
      private final String typeName;
      private final VisibleClassMetadata classMetadata;

      public String getName() {
         return name;
      }

      public String getTypeName() {
         return typeName;
      }

      public PropertyReference getReferencedProperty() {
         if(classMetadata == null) return null;
         return new PropertyReference(classMetadata);
      }

      public VisiblePropertyMetadata(String name, String typeName, VisibleClassMetadata classMetadata) {
         super();
         this.name = name;
         this.typeName = typeName;
         this.classMetadata = classMetadata;
      }
   }

   public static class PropertyReference {
      private final VisibleClassMetadata metadata;

      public String getTypeName() {
         return metadata.getTypeName();
      }

      public TypeUrlsViewModel getUrls() {
         return metadata.getUrls();
      }

      public PropertyReference(VisibleClassMetadata metadata) {
         this.metadata = metadata;
      }
   }

}
