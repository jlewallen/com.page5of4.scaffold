package com.page5of4.scaffold;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UrlsViewModel extends TypeUrlsViewModel {
   private final String showUrl;
   private final String updateUrl;
   private final String updateFormUrl;
   private final String deleteUrl;

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

   public static class RelativeUrlSerializer extends JsonSerializer<String> {
      public HttpServletRequest getServletRequest() {
         return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
      }

      public String getRootURL() {
         HttpServletRequest servletRequest = getServletRequest();
         try {
            return new URL(servletRequest.getScheme(), servletRequest.getLocalName(), servletRequest.getLocalPort(), servletRequest.getContextPath()).toString();
         }
         catch(MalformedURLException e) {
            throw new RuntimeException("Unable to get Current URL", e);
         }
      }

      @Override
      public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
         if(value != null) {
            jgen.writeString(getRootURL() + value.toString());
         }
      }
   }
}
