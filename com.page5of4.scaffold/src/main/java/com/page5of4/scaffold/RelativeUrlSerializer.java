package com.page5of4.scaffold;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RelativeUrlSerializer extends JsonSerializer<String> {
   public HttpServletRequest getServletRequest() {
      return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
   }

   public String buildURL(String path) {
      HttpServletRequest servletRequest = getServletRequest();
      try {
         return new URL(servletRequest.getScheme(), servletRequest.getLocalName(), servletRequest.getLocalPort(), servletRequest.getContextPath() + path).toString();
      }
      catch(MalformedURLException e) {
         throw new RuntimeException("Unable to get Current URL", e);
      }
   }

   @Override
   public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
      if(value != null) {
         jgen.writeString(buildURL(value.toString()));
      }
   }
}