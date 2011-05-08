package com.ss.scaffold;

public class ManyToOnePropertyMetadata extends AssociationMetadata {

   private Object[] items;

   public Object[] getItems() {
      return items;
   }

   public ManyToOnePropertyMetadata(PropertyMetadata property, Object[] items) {
      super(property, property.getPropertyType());
      this.items = items;
   }

   public static ManyToOnePropertyMetadata create(PropertyMetadata property) {
      ManyToOnePropertyMetadata metadata = tryCreate(property);
      if(metadata != null) {
         return metadata;
      }
      throw new RuntimeException("Invalid ManyToOne property type: " + property);
   }

   public static ManyToOnePropertyMetadata tryCreate(PropertyMetadata property) {
      Class<? extends Object> type = property.getPropertyType();
      if(type.isEnum()) {
         return new ManyToOnePropertyMetadata(property, type.getEnumConstants());
      }
      return null;
   }

}
