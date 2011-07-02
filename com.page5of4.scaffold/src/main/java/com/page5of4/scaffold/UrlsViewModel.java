package com.page5of4.scaffold;

public class UrlsViewModel {
   private final String indexUrl;
   private final String createUrl;
   private final String createFormUrl;
   private final String showUrl;
   private final String updateUrl;
   private final String updateFormUrl;
   private final String deleteUrl;

   public String getIndexUrl() {
      return indexUrl;
   }

   public String getCreateUrl() {
      return createUrl;
   }

   public String getCreateFormUrl() {
      return createFormUrl;
   }

   public String getShowUrl() {
      return showUrl;
   }

   public String getUpdateUrl() {
      return updateUrl;
   }

   public String getUpdateFormUrl() {
      return updateFormUrl;
   }

   public String getDeleteUrl() {
      return deleteUrl;
   }

   public UrlsViewModel(String indexUrl, String createUrl, String createFormUrl, String showUrl, String updateUrl, String updateFormUrl, String deleteUrl) {
      super();
      this.indexUrl = indexUrl;
      this.createUrl = createUrl;
      this.createFormUrl = createFormUrl;
      this.showUrl = showUrl;
      this.updateUrl = updateUrl;
      this.updateFormUrl = updateFormUrl;
      this.deleteUrl = deleteUrl;
   }

   public UrlsViewModel(String indexUrl, String createUrl, String createFormUrl) {
      super();
      this.indexUrl = indexUrl;
      this.createUrl = createUrl;
      this.createFormUrl = createFormUrl;
      this.showUrl = null;
      this.updateUrl = null;
      this.updateFormUrl = null;
      this.deleteUrl = null;
   }
}
