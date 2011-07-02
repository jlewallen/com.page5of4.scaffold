package com.page5of4.scaffold;

import java.beans.PropertyDescriptor;

public class OneToManyPropertyMetadata extends AssociationMetadata {

   private Object[] items;

   public Object[] getItems() {
      return items;
   }

   public OneToManyPropertyMetadata(PropertyDescriptor property, Object[] items) {
      super(property);
      this.items = items;
   }

}
