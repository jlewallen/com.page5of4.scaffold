package com.page5of4.scaffold.properties;

public class PropertyTokenHolder {

   private String canonicalName;

   private String actualName;

   private String[] keys;

   public String getCanonicalName() {
      return canonicalName;
   }

   public String getActualName() {
      return actualName;
   }

   public String[] getKeys() {
      return keys;
   }

   public PropertyTokenHolder(String canonicalName, String actualName, String[] keys) {
      super();
      this.canonicalName = canonicalName;
      this.actualName = actualName;
      this.keys = keys;
   }

}
