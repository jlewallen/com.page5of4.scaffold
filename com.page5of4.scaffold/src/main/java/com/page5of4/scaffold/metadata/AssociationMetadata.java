package com.page5of4.scaffold.metadata;

import java.beans.PropertyDescriptor;

public abstract class AssociationMetadata {

   private final PropertyDescriptor property;

   public PropertyDescriptor getProperty() {
      return property;
   }

   public Class<? extends Object> getPropertyType() {
      return property.getPropertyType();
   }

   public AssociationMetadata(PropertyDescriptor property) {
      super();
      this.property = property;
   }

}
