package com.page5of4.scaffold.metadata;

import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class InstanceMetadata extends TemplateMetadata {
   private final Object targetObject;
   private final AbstractMetadata currentMetadata;

   public Object getTargetObject() {
      return targetObject;
   }

   public Object getDisplayValue() {
      return getCurrentPropertyMetadata().getDisplayValue(targetObject);
   }

   public UrlsViewModel getEntityUrls() {
      return getUrls();
   }

   public AbstractMetadata getCurrentMetadata() {
      return currentMetadata;
   }

   public PropertyMetadata getCurrentPropertyMetadata() {
      return (PropertyMetadata)getCurrentMetadata();
   }

   public InstanceMetadata(ClassMetadata classMetadata, AbstractMetadata currentMetadata, Object targetObject, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      super(classMetadata, scaffoldViewModel, urlsViewModel);
      this.targetObject = targetObject;
      this.currentMetadata = currentMetadata;
   }
}