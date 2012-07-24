package com.page5of4.scaffold.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.TypeUrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class ExternalMetadataFactory {

   private static final Logger logger = LoggerFactory.getLogger(ExternalMetadataFactory.class);
   private final Validator validator;
   private final MetadataResolver metadataResolver;
   private final ScaffoldViewModelFactory viewModelFactory;

   @Autowired
   public ExternalMetadataFactory(MetadataResolver metadataResolver, ScaffoldViewModelFactory viewModelFactory) {
      super();
      this.metadataResolver = metadataResolver;
      this.viewModelFactory = viewModelFactory;
      ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
      validator = validatorFactory.getValidator();
   }

   public VisibleClassMetadata createVisibleMetadata(Class<?> objectClass) {
      BeanDescriptor beanDescriptor = validator.getConstraintsForClass(objectClass);
      Set<javax.validation.metadata.PropertyDescriptor> constrainedProperties = beanDescriptor.getConstrainedProperties();
      ClassMetadata classMetadata = metadataResolver.resolve(objectClass);
      List<VisiblePropertyMetadata> properties = new ArrayList<VisiblePropertyMetadata>();
      for(PropertyMetadata pm : classMetadata.getProperties()) {
         properties.add(createVisiblePropertyMetadata(pm, constrainedProperties));
      }
      ScaffoldViewModel viewModel = viewModelFactory.createScaffoldViewModel(objectClass);
      TypeUrlsViewModel urls = new TypeUrlsViewModel(viewModelFactory.createUrlsViewModel(viewModel, objectClass));
      return new VisibleClassMetadata(classMetadata.getName(), objectClass.getName(), urls, properties);
   }

   private PropertyReference createPropertyReference(Class<?> objectClass) {
      ScaffoldViewModel scaffoldViewModel = viewModelFactory.createScaffoldViewModel(objectClass);
      return new PropertyReference(objectClass.getName(), viewModelFactory.createUrlsViewModel(scaffoldViewModel, objectClass));
   }

   private VisiblePropertyMetadata createVisiblePropertyMetadata(PropertyMetadata pm, Set<javax.validation.metadata.PropertyDescriptor> constrainedProperties) {
      PropertyReference reference = null;
      if(pm.getPropertyTypeHasMetadata()) {
         reference = createPropertyReference(pm.getPropertyTypeMetadata().getObjectClass());
      }
      List<VisiblePropertyConstraint> constraints = new ArrayList<VisiblePropertyConstraint>();
      for(javax.validation.metadata.PropertyDescriptor pd : constrainedProperties) {
         if(pd.getPropertyName().equals(pm.getName()) && pd.hasConstraints()) {
            for(ConstraintDescriptor<?> cd : pd.getConstraintDescriptors()) {
               constraints.add(new VisiblePropertyConstraint(cd));
            }
         }
      }
      return new VisiblePropertyMetadata(pm.getName(), pm.getPropertyType().getName(), constraints, reference);
   }

   public static class VisiblePropertyConstraint {
      private final String name;
      private final Map<String, Object> attributes;

      public String getName() {
         return name;
      }

      public Map<String, Object> getAttributes() {
         return attributes;
      }

      public VisiblePropertyConstraint(ConstraintDescriptor<?> cd) {
         this.name = cd.getAnnotation().annotationType().getName();
         this.attributes = new HashMap<String, Object>();
      }
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
      private final List<VisiblePropertyConstraint> constraints;
      private final PropertyReference propertyReference;

      public String getName() {
         return name;
      }

      public String getTypeName() {
         return typeName;
      }

      public List<VisiblePropertyConstraint> getConstraints() {
         return constraints;
      }

      public PropertyReference getReferencedProperty() {
         return propertyReference;
      }

      public VisiblePropertyMetadata(String name, String typeName, List<VisiblePropertyConstraint> constraints, PropertyReference propertyReference) {
         super();
         this.name = name;
         this.typeName = typeName;
         this.constraints = constraints;
         this.propertyReference = propertyReference;
      }
   }

   public static class PropertyReference {
      private final String typeName;
      private final TypeUrlsViewModel urls;

      public String getTypeName() {
         return typeName;
      }

      public TypeUrlsViewModel getUrls() {
         return urls;
      }

      public PropertyReference(String typeName, TypeUrlsViewModel urls) {
         this.typeName = typeName;
         this.urls = urls;
      }
   }

}
