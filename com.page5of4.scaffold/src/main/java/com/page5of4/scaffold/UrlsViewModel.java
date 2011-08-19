package com.page5of4.scaffold;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class UrlsViewModel extends TypeUrlsViewModel {
   private final String showUrl;
   private final String updateUrl;
   private final String updateFormUrl;
   private final String deleteUrl;

   @JsonSerialize(using = RelativeUrlSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
   public String getShowUrl() {
      return showUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
   public String getUpdateUrl() {
      return updateUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
   public String getUpdateFormUrl() {
      return updateFormUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
   public String getDeleteUrl() {
      return deleteUrl;
   }

   public UrlsViewModel(String indexUrl, String createUrl, String createFormUrl, String metaUrl, String showUrl, String updateUrl, String updateFormUrl, String deleteUrl) {
      super(indexUrl, createUrl, createFormUrl, metaUrl);
      this.showUrl = showUrl;
      this.updateUrl = updateUrl;
      this.updateFormUrl = updateFormUrl;
      this.deleteUrl = deleteUrl;
   }

   public UrlsViewModel(String indexUrl, String createUrl, String createFormUrl, String metaUrl) {
      super(indexUrl, createUrl, createFormUrl, metaUrl);
      this.showUrl = null;
      this.updateUrl = null;
      this.updateFormUrl = null;
      this.deleteUrl = null;
   }
}
