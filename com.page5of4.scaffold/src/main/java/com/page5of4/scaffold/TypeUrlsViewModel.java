package com.page5of4.scaffold;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.page5of4.scaffold.UrlsViewModel.RelativeUrlSerializer;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TypeUrlsViewModel {
   private final String indexUrl;
   private final String createUrl;
   private final String createFormUrl;
   private final String metaUrl;

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getIndexUrl() {
      return indexUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getCreateUrl() {
      return createUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getCreateFormUrl() {
      return createFormUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getMetaUrl() {
      return metaUrl;
   }

   public TypeUrlsViewModel(UrlsViewModel urls) {
      this(urls.getIndexUrl(), urls.getCreateUrl(), urls.getCreateFormUrl(), urls.getMetaUrl());
   }

   public TypeUrlsViewModel(String indexUrl, String createUrl, String createFormUrl, String metaUrl) {
      super();
      this.indexUrl = indexUrl;
      this.createUrl = createUrl;
      this.createFormUrl = createFormUrl;
      this.metaUrl = metaUrl;
   }
}
