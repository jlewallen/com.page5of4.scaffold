package com.ss.scaffold;

public class LabelAndValueModel implements LabelAndValue {

   private String label;
   private Object value;

   public String getLabel() {
      return label;
   }

   public Object getValue() {
      return value;
   }

   public LabelAndValueModel(String label, Object value) {
      super();
      this.label = label;
      this.value = value;
   }

   @Override
   public int hashCode() {
      return value.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null) return false;
      if(getClass() != obj.getClass()) return false;
      LabelAndValueModel other = (LabelAndValueModel)obj;
      if(value == null) {
         if(other.value != null) return false;
      }
      else if(!value.equals(other.value)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "LabelAndValueModel [label=" + label + ", value=" + value + "]";
   }

}
