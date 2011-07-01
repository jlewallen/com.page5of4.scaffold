package com.page5of4.scaffold;

import java.util.ArrayList;
import java.util.List;

import com.page5of4.scaffold.web.ScaffoldViewModel;

public class TemplateMetadata {

   private final ClassMetadata classMetadata;
   private final AbstractMetadata currentMetadata;
   private final Object targetObject;
   private final List<?> targetCollection;
   private final ScaffoldViewModel scaffoldViewModel;

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

   public List<TemplateMetadata> getTargetCollectionMetas() {
      List<TemplateMetadata> collection = new ArrayList<TemplateMetadata>();
      for(Object row : targetCollection) {
         collection.add(new TemplateMetadata(classMetadata, currentMetadata, row, collection, scaffoldViewModel));
      }
      return collection;
   }

   public TemplateMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, Object targetObject, List<?> targetCollection, ScaffoldViewModel scaffoldViewModel) {
      super();
      this.classMetadata = classMetadata;
      this.currentMetadata = currentMetadata;
      this.targetObject = targetObject;
      this.targetCollection = targetCollection;
      this.scaffoldViewModel = scaffoldViewModel;
   }

   public Object getDisplayValue() {
      return getCurrentPropertyMetadata().getDisplayValue(targetObject);
   }

   public String getCreateUrl() {
      return scaffoldViewModel.getCreateUrl();
   }

   public String getIndexUrl() {
      return scaffoldViewModel.getIndexUrl();
   }

   public String getShowUrl() {
      return scaffoldViewModel.getShowUrl(targetObject);
   }

   public String getUpdateUrl() {
      return scaffoldViewModel.getUpdateUrl(targetObject);
   }

   public String getDeleteUrl() {
      return scaffoldViewModel.getDeleteUrl(targetObject);
   }

}
