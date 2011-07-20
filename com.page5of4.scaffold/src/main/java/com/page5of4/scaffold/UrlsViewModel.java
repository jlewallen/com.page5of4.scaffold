package com.page5of4.scaffold;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class UrlsViewModel {
   private final String indexUrl;
   private final String createUrl;
   private final String createFormUrl;
   private final String showUrl;
   private final String updateUrl;
   private final String updateFormUrl;
   private final String deleteUrl;

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
   public String getShowUrl() {
      return showUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getUpdateUrl() {
      return updateUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
   public String getUpdateFormUrl() {
      return updateFormUrl;
   }

   @JsonSerialize(using = RelativeUrlSerializer.class)
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

   public static class RelativeUrlSerializer extends JsonSerializer<String> {

      @Override
      public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
         jgen.writeString("http://127.0.0.1:8080/" + value.toString());
      }

   }
}
