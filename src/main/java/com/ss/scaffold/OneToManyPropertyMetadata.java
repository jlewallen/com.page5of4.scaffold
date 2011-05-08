package com.ss.scaffold;

public class OneToManyPropertyMetadata extends AssociationMetadata {

   private Object[] items;

   public Object[] getItems() {
      return items;
   }

   public OneToManyPropertyMetadata(PropertyMetadata property, Object[] items) {
      super(property, property.getPropertyType());
      this.items = items;
   }

   public static OneToManyPropertyMetadata create(PropertyMetadata property) {
      OneToManyPropertyMetadata metadata = tryCreate(property);
      if(metadata != null) {
         return metadata;
      }
      throw new RuntimeException("Invalid OneToMany property type: " + property);
   }

   public static OneToManyPropertyMetadata tryCreate(PropertyMetadata property) {
      Class<? extends Object> type = property.getPropertyType();
      return null;
   }

}
