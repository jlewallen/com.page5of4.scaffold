package com.page5of4.scaffold.metadata;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.ManyToOne;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.FallbackLabelAndValue;
import com.page5of4.scaffold.LabelAndValue;
import com.page5of4.scaffold.LabelAndValueModel;
import com.page5of4.scaffold.ReflectionUtils;
import com.page5of4.scaffold.domain.Repository;

@Service
public class CachingMetadataResolver implements MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(CachingMetadataResolver.class);

   private final Map<Class<?>, ClassMetadata> cache = new ConcurrentHashMap<Class<?>, ClassMetadata>();
   private final ConversionService conversionService;
   private final Repository repository;

   @Autowired
   public CachingMetadataResolver(ConversionService conversionService, Repository repository) {
      super();
      this.conversionService = conversionService;
      this.repository = repository;
   }

   public ClassMetadata resolve(Class<?> objectClass) throws IntrospectionException {
      if(objectClass == null) throw new IllegalArgumentException("Cannot resolve NULL object class.");
      ClassMetadata metadata = cache.get(objectClass);
      if(metadata == null) {
         logger.debug("Resolving {}", objectClass);
         metadata = create(objectClass);
         cache.put(objectClass, metadata);
      }
      return metadata;
   }

   private ClassMetadata create(Class<?> objectClass) throws IntrospectionException {
      List<PropertyMetadata> properties = getProperties(objectClass);
      return new ClassMetadata(objectClass, properties);
   }

   private List<PropertyMetadata> getProperties(Class<? extends Object> objectClass) throws IntrospectionException {
      List<PropertyMetadata> properties = new ArrayList<PropertyMetadata>();
      BeanInfo beanInfo = Introspector.getBeanInfo(objectClass);
      for(PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
         if(!shouldSkip(descriptor)) {
            try {
               OneToManyPropertyMetadata oneToMany = createOneToMany(objectClass, descriptor);
               ManyToOnePropertyMetadata manyToOne = createManyToOne(objectClass, descriptor);
               properties.add(new PropertyMetadata(conversionService, objectClass, descriptor, oneToMany, manyToOne));
            }
            catch(Exception error) {
               throw new RuntimeException(String.format("Error resolving property metadata for: '%s'", descriptor.getName()), error);
            }
         }
      }
      Collections.sort(properties, new Comparator<PropertyMetadata>() {
         @Override
         public int compare(PropertyMetadata o1, PropertyMetadata o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return properties;
   }

   public static boolean shouldSkip(PropertyDescriptor descriptor) {
      String[] skip = new String[] { "class" };
      return ArrayUtils.contains(skip, descriptor.getName());
   }

   public ManyToOnePropertyMetadata createManyToOne(Class<? extends Object> objectClass, PropertyDescriptor descriptor) {
      Class<? extends Object> type = descriptor.getPropertyType();
      if(type.isEnum()) {
         return new ManyToOnePropertyMetadata(descriptor, type.getEnumConstants());
      }
      ManyToOne manyToOneAnnotation = ReflectionUtils.getFieldOrMethodAnnotation(ManyToOne.class, objectClass, descriptor);
      if(manyToOneAnnotation == null) {
         return null;
      }
      Collection<?> found = repository.findAll(type);
      if(found != null) {
         List<LabelAndValue> items = new ArrayList<LabelAndValue>();
         if(shouldIncludeEmpty()) {
            items.add(new LabelAndValueModel("", "", null));
         }
         if(conversionService.canConvert(type, LabelAndValue.class)) {
            for(Object value : found) {
               items.add(conversionService.convert(value, LabelAndValue.class));
            }
         }
         else if(LabelAndValue.class.isAssignableFrom(type)) {
            for(Object value : found) {
               items.add((LabelAndValue)value);
            }
         }
         else {
            for(Object value : found) {
               items.add(new FallbackLabelAndValue(value));
            }
         }
         return new ManyToOnePropertyMetadata(descriptor, items.toArray(new LabelAndValue[0]));
      }
      return null;
   }

   private static boolean shouldIncludeEmpty() {
      return true;
   }

   public OneToManyPropertyMetadata createOneToMany(Class<? extends Object> objectClass, PropertyDescriptor descriptor) {
      return null;
   }

}
