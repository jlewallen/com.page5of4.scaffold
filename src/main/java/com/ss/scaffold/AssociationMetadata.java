package com.ss.scaffold;

public abstract class AssociationMetadata {

   private final PropertyMetadata property;
   private final Class<? extends Object> propertyType;

   public PropertyMetadata getProperty() {
      return property;
   }

   public Class<? extends Object> getPropertyType() {
      return propertyType;
   }

   public AssociationMetadata(PropertyMetadata property, Class<? extends Object> propertyType) {
      super();
      this.property = property;
      this.propertyType = propertyType;
   }

}
