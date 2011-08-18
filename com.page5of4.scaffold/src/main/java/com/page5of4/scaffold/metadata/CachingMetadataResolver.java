package com.page5of4.scaffold.metadata;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.configuration.Configurer;

@Service
public class CachingMetadataResolver implements MetadataResolver {

   private static final Logger logger = LoggerFactory.getLogger(CachingMetadataResolver.class);

   private static final ThreadLocal<Map<Class<? extends Object>, ClassMetadata>> stack = new ThreadLocal<Map<Class<? extends Object>, ClassMetadata>>();
   private final Map<Class<?>, ClassMetadata> cache = new ConcurrentHashMap<Class<?>, ClassMetadata>();
   private final ConversionService conversionService;
   private final Configurer configurer;

   @Autowired
   public CachingMetadataResolver(ConversionService conversionService, Configurer configurer) {
      super();
      this.conversionService = conversionService;
      this.configurer = configurer;
   }

   public ClassMetadata resolve(Class<?> objectClass) {
      if(objectClass == null) throw new IllegalArgumentException("Cannot resolve NULL object class.");
      ClassMetadata metadata = cache.get(objectClass);
      if(metadata == null) {
         try {
            logger.debug("Resolving {}", objectClass);
            metadata = create(objectClass);
            logger.debug("Done resolving {}", objectClass);
            cache.put(objectClass, metadata);
         }
         catch(IntrospectionException error) {
            throw new RuntimeException(error);
         }
      }
      return metadata;
   }

   private ClassMetadata create(Class<?> objectClass) throws IntrospectionException {
      List<PropertyMetadata> properties = createProperties(objectClass);
      return new ClassMetadata(objectClass, properties);
   }

   private List<PropertyMetadata> createProperties(Class<? extends Object> objectClass) throws IntrospectionException {
      List<PropertyMetadata> properties = new ArrayList<PropertyMetadata>();
      BeanInfo beanInfo = Introspector.getBeanInfo(objectClass);
      for(PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
         if(!shouldSkip(descriptor)) {
            try {
               properties.add(createProperty(objectClass, descriptor));
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

   private Class<?> getCollectionTypeIfAny(PropertyDescriptor descriptor) {
      Class<?> propertyType = descriptor.getPropertyType();
      if(!Collection.class.isAssignableFrom(propertyType)) {
         return null;
      }
      ParameterizedType collectionParameterizedType = (ParameterizedType)descriptor.getReadMethod().getGenericReturnType();
      return (Class<?>)collectionParameterizedType.getActualTypeArguments()[0];
   }

   public static class FullResolver implements PropertyTypeMetadataResolver {
      private final MetadataResolver resolver;
      private final Class<?> type;

      public FullResolver(MetadataResolver resolver, Class<?> type) {
         super();
         this.resolver = resolver;
         this.type = type;
      }

      @Override
      public ClassMetadata resolve() {
         return resolver.resolve(type);
      }
   }

   private PropertyMetadata createProperty(Class<? extends Object> objectClass, PropertyDescriptor descriptor) throws Exception {
      Class<?> propertyType = getCollectionTypeIfAny(descriptor);
      if(propertyType == null) {
         propertyType = descriptor.getPropertyType();
      }
      if(configurer.findAllScaffoldClasses().contains(propertyType)) {
         PropertyTypeMetadataResolver resolver = new FullResolver(this, propertyType);
         return new PropertyMetadata(conversionService, resolver, objectClass, descriptor);
      }
      return new PropertyMetadata(conversionService, null, objectClass, descriptor);
   }

   public static boolean shouldSkip(PropertyDescriptor descriptor) {
      String[] skip = new String[] { "class" };
      return ArrayUtils.contains(skip, descriptor.getName());
   }

}
