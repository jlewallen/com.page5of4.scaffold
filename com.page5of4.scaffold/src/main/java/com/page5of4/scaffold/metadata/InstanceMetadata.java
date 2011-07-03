package com.page5of4.scaffold.metadata;

import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class InstanceMetadata extends TemplateMetadata {
   private final Object targetObject;

   public Object getTargetObject() {
      return targetObject;
   }

   public UrlsViewModel getEntityUrls() {
      return getUrls();
   }

   public InstanceMetadata(ClassMetadata classMetadata, Object targetObject, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      super(classMetadata, scaffoldViewModel, urlsViewModel);
      this.targetObject = targetObject;
   }
}
