package com.page5of4.scaffold;

import java.util.List;

public class TemplateMetadata {

   private final ClassMetadata classMetadata;
   private final AbstractMetadata currentMetadata;
   private final Object targetObject;
   private final List<?> targetCollection;

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public AbstractMetadata getCurrentMetadata() {
      return currentMetadata;
   }

   public PropertyMetadata getCurrentPropertyMetadata() {
      return (PropertyMetadata)getCurrentMetadata();
   }

   public Object getTargetObject() {
      return targetObject;
   }

   public List<?> getTargetCollection() {
      return targetCollection;
   }

   public TemplateMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, Object targetObject, List<?> targetCollection) {
      super();
      this.classMetadata = classMetadata;
      this.currentMetadata = currentMetadata;
      this.targetObject = targetObject;
      this.targetCollection = targetCollection;
   }

   public Object getDisplayValue() {
      return getCurrentPropertyMetadata().getDisplayValue(targetObject);
   }

}
