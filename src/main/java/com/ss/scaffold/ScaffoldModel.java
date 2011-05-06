package com.ss.scaffold;

public class ScaffoldModel {

   private String mode;
   private String templatePrefix;
   private String formPrefix;
   private AbstractMetadata meta;

   public String getMode() {
      return mode;
   }

   public void setMode(String mode) {
      this.mode = mode;
   }

   public String getTemplatePrefix() {
      return templatePrefix;
   }

   public void setTemplatePrefix(String templatePrefix) {
      this.templatePrefix = templatePrefix;
   }

   public String getFormPrefix() {
      return formPrefix;
   }

   public void setFormPrefix(String formPrefix) {
      this.formPrefix = formPrefix;
   }

   public AbstractMetadata getMeta() {
      return meta;
   }

   public void setMeta(AbstractMetadata meta) {
      this.meta = meta;
   }

   public ScaffoldModel(String mode, String templatePrefix, String formPrefix, AbstractMetadata meta) {
      super();
      this.mode = mode;
      this.templatePrefix = templatePrefix;
      this.formPrefix = formPrefix;
      this.meta = meta;
   }

}
