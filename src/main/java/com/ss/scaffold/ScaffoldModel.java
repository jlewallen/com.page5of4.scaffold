package com.ss.scaffold;

public class ScaffoldModel {

   private String mode;
   private String templatePrefix;
   private String formPrefix;
   private Object targetObject;
   private String propertyName;
   private ClassMetadata classMetadata;
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

   public Object getTargetObject() {
      return targetObject;
   }

   public void setTargetObject(Object targetObject) {
      this.targetObject = targetObject;
   }

   public String getPropertyName() {
      return propertyName;
   }

   public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
   }

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public void setClassMetadata(ClassMetadata classMetadata) {
      this.classMetadata = classMetadata;
   }

   public AbstractMetadata getMeta() {
      return meta;
   }

   public void setMeta(AbstractMetadata meta) {
      this.meta = meta;
   }

   public ScaffoldModel(String mode, String templatePrefix, String formPrefix, Object targetObject, String propertyName, ClassMetadata classMetadata) {
      super();
      this.mode = mode;
      this.templatePrefix = templatePrefix;
      this.formPrefix = formPrefix;
      this.targetObject = targetObject;
      this.propertyName = propertyName;
      this.classMetadata = classMetadata;
   }

}
