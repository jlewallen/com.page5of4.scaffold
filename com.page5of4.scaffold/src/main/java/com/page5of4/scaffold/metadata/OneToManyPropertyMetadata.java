package com.page5of4.scaffold.metadata;

import java.beans.PropertyDescriptor;

public class OneToManyPropertyMetadata extends AssociationMetadata {

   private final Object[] items;
   private final String itemLabelExpression;
   private final String itemValueExpression;
   private final boolean nullable;

   public Object[] getItems() {
      return items;
   }

   public boolean isNullable() {
      return nullable;
   }

   public String getItemLabelExpression() {
      return itemLabelExpression;
   }

   public String getItemValueExpression() {
      return itemValueExpression;
   }

   public OneToManyPropertyMetadata(PropertyDescriptor property, Object[] items, boolean nullable) {
      this(property, items, nullable, null, null);
   }

   public OneToManyPropertyMetadata(PropertyDescriptor property, Object[] items, boolean nullable, String itemLabelExpression, String itemValueExpression) {
      super(property);
      this.items = items;
      this.nullable = nullable;
      this.itemLabelExpression = itemLabelExpression;
      this.itemValueExpression = itemValueExpression;
   }

}
