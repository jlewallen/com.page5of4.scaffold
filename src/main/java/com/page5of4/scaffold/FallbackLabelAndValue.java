package com.page5of4.scaffold;

public class FallbackLabelAndValue implements LabelAndValue {

   private final Object object;

   public FallbackLabelAndValue(Object object) {
      super();
      this.object = object;
   }

   @Override
   public Object getValue() {
      if(object == null) return "";
      return object.toString();
   }

   @Override
   public String getLabel() {
      if(object == null) return "";
      return object.toString();
   }

   @Override
   public Object getActualValue() {
      return object;
   }

   @Override
   public int hashCode() {
      if(object == null) return 0;
      return object.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null) return false;
      if(getClass() != obj.getClass()) return false;
      FallbackLabelAndValue other = (FallbackLabelAndValue)obj;
      if(object == null) {
         if(other.object != null) return false;
      }
      else if(!object.equals(other.object)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "FallbackLabelAndValue [object=" + object + "]";
   }
}
