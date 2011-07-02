package com.page5of4.scaffold;

import com.page5of4.scaffold.web.ScaffoldViewModel;

public class TemplateMetadata {

   private final ClassMetadata classMetadata;
   private final ScaffoldViewModel scaffoldViewModel;
   private final UrlsViewModel urlsViewModel;

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public ScaffoldViewModel getViewModel() {
      return scaffoldViewModel;
   }

   public UrlsViewModel getUrls() {
      return urlsViewModel;
   }

   public TemplateMetadata(ClassMetadata classMetadata, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      super();
      this.classMetadata = classMetadata;
      this.scaffoldViewModel = scaffoldViewModel;
      this.urlsViewModel = urlsViewModel;
   }

}
