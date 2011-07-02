package com.page5of4.scaffold.web;

public class ScaffoldViewModel {
   private final String resourceName;
   private final String collectionName;

   public String getResourceName() {
      return resourceName;
   }

   public String getResourceTitle() {
      return com.page5of4.scaffold.StringUtils.titlize(resourceName);
   }

   public String getCollectionName() {
      return collectionName;
   }

   public String getCollectionTitle() {
      return com.page5of4.scaffold.StringUtils.titlize(collectionName);
   }

   public String getViewsRoot() {
      return getResourceName() + "/";
   }

   public String getShowView() {
      return getViewsRoot() + "show";
   }

   public String getIndexView() {
      return getViewsRoot() + "index";
   }

   public String getFormView() {
      return getViewsRoot() + "form";
   }

   public ScaffoldViewModel(String resourceName, String resourceCollectionName) {
      super();
      this.resourceName = resourceName;
      this.collectionName = resourceCollectionName;
   }
}
