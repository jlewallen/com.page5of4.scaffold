package com.ss.scaffold;

public class MultivaluedPropertyMetadata {

   private final PropertyMetadata property;
   private Class<? extends Object> propertyType;
   private Object[] items;

   public PropertyMetadata getProperty() {
      return property;
   }

   public Class<? extends Object> getPropertyType() {
      return propertyType;
   }

   public Object[] getItems() {
      return items;
   }

   public MultivaluedPropertyMetadata(PropertyMetadata property, Object[] items) {
      super();
      this.property = property;
      this.propertyType = property.getPropertyType();
      this.items = items;
   }

   public static MultivaluedPropertyMetadata create(PropertyMetadata property) {
      Class<? extends Object> type = property.getPropertyType();
      if(type.isEnum()) {
         return new MultivaluedPropertyMetadata(property, type.getEnumConstants());
      }
      throw new RuntimeException("Invalid multivalued property type: " + type);
   }

}
