package com.page5of4.scaffold.web;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.page5of4.scaffold.Finders;

public class ScaffoldViewModel {
   private final HttpServletRequest servletRequest;
   private final String resourceName;
   private final String collectionName;

   private String getPathInfo() {
      return servletRequest.getPathInfo();
   }

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

   public String getIndexUrl() {
      return String.format("%s", getCollectionName());
   }

   public String getCreateUrl() {
      return String.format("%s/form", getCollectionName());
   }

   public String getShowUrl(Object object) {
      return String.format("%s/%s", getCollectionName(), getId(object));
   }

   public String getUpdateUrl(Object object) {
      return String.format("%s/%s/form", getCollectionName(), getId(object));
   }

   public String getDeleteUrl(Object object) {
      return String.format("%s/%s", getCollectionName(), getId(object));
   }

   private Object getId(Object object) {
      try {
         Method idGetter = Finders.findIdGetter(object.getClass());
         return idGetter.invoke(object);
      }
      catch(Exception error) {
         throw new RuntimeException("Error getting Id: " + object, error);
      }
   }

   public ScaffoldViewModel(String resourceName, String resourceCollectionName, HttpServletRequest servletRequest) {
      super();
      this.servletRequest = servletRequest;
      this.resourceName = resourceName;
      this.collectionName = resourceCollectionName;
   }
}
