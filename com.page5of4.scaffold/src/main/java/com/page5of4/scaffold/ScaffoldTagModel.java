package com.page5of4.scaffold;

import java.util.List;

import com.page5of4.scaffold.metadata.ClassMetadata;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class ScaffoldTagModel {

   private final String formPrefix;
   private final String[] modes;
   private final String templatePrefix;
   private final ScaffoldViewModel scaffoldViewModel;
   private ClassMetadata classMetadata;
   private Class<?> objectClass;
   private String propertyName;
   private List<?> targetCollection;
   private Object targetObject;

   public ScaffoldTagModel(String[] modes, String templatePrefix, String formPrefix, Class<?> objectClass, List<?> targetCollection, String propertyName, ClassMetadata classMetadata,
         ScaffoldViewModel scaffoldViewModel) {
      super();
      this.modes = modes;
      this.templatePrefix = templatePrefix;
      this.formPrefix = formPrefix;
      this.objectClass = objectClass;
      this.targetCollection = targetCollection;
      this.propertyName = propertyName;
      this.classMetadata = classMetadata;
      this.scaffoldViewModel = scaffoldViewModel;
   }

   public ScaffoldTagModel(String[] modes, String templatePrefix, String formPrefix, Object targetObject, String propertyName, ClassMetadata classMetadata, ScaffoldViewModel scaffoldViewModel) {
      super();
      this.modes = modes;
      this.templatePrefix = templatePrefix;
      this.formPrefix = formPrefix;
      this.targetObject = targetObject;
      this.propertyName = propertyName;
      this.classMetadata = classMetadata;
      this.scaffoldViewModel = scaffoldViewModel;
   }

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public String getFormPrefix() {
      return formPrefix;
   }

   public String[] getModes() {
      return modes;
   }

   public Class<?> getObjectClass() {
      return objectClass;
   }

   public String getPropertyName() {
      return propertyName;
   }

   public List<?> getTargetCollection() {
      return targetCollection;
   }

   public Object getTargetObject() {
      return targetObject;
   }

   public String getTemplatePrefix() {
      return templatePrefix;
   }

   public void setClassMetadata(ClassMetadata classMetadata) {
      this.classMetadata = classMetadata;
   }

   public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
   }

   public void setTargetObject(Object targetObject) {
      this.targetObject = targetObject;
   }

   public ScaffoldViewModel getScaffoldViewModel() {
      return scaffoldViewModel;
   }

   public Class<?> determineObjectClass() {
      if(objectClass != null) {
         return objectClass;
      }
      if(targetObject == null) {
         throw new IllegalArgumentException("Unable to determine Object Class");
      }
      return targetObject.getClass();
   }

}
