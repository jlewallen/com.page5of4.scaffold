package com.page5of4.scaffold;

import java.util.List;

import com.page5of4.scaffold.web.ScaffoldViewModel;

public class TemplateMetadata {

   private final ClassMetadata classMetadata;
   private final AbstractMetadata currentMetadata;
   private final Object targetObject;
   private final List<?> targetCollection;
   private final List<TemplateMetadata> targetCollectionMetas;
   private final ScaffoldViewModel scaffoldViewModel;
   private final UrlsViewModel urlsViewModel;

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
      return targetCollectionMetas;
   }

   public ScaffoldViewModel getViewModel() {
      return scaffoldViewModel;
   }

   public Object getDisplayValue() {
      return getCurrentPropertyMetadata().getDisplayValue(targetObject);
   }

   public UrlsViewModel getUrls() {
      return urlsViewModel;
   }

   public UrlsViewModel getEntityUrls() {
      return urlsViewModel;
   }

   public TemplateMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, Object targetObject, List<?> targetCollection, List<TemplateMetadata> targetCollectionMetas,
         ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      super();
      this.targetCollectionMetas = targetCollectionMetas;
      this.urlsViewModel = urlsViewModel;
      this.classMetadata = classMetadata;
      this.currentMetadata = currentMetadata;
      this.targetObject = targetObject;
      this.targetCollection = targetCollection;
      this.scaffoldViewModel = scaffoldViewModel;
   }

   public TemplateMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, Object targetObject, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      this(classMetadata, currentMetadata, targetObject, null, null, scaffoldViewModel, urlsViewModel);
   }

   public TemplateMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, List<?> targetCollection, List<TemplateMetadata> targetCollectionMetas, ScaffoldViewModel scaffoldViewModel,
         UrlsViewModel urlsViewModel) {
      this(classMetadata, currentMetadata, null, targetCollection, targetCollectionMetas, scaffoldViewModel, urlsViewModel);
   }

}
