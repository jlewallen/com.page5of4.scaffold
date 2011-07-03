package com.page5of4.scaffold.web;

import java.util.Collection;

public class Resources<T extends Object> {
   private final Collection<T> resources;
   private final Class<T> resourceClass;
   private final int numberOfPages;
   private final int page;

   public Class<T> getResourceClass() {
      return resourceClass;
   }

   public Collection<T> getResources() {
      return resources;
   }

   public int getNumberOfPages() {
      return numberOfPages;
   }

   public int getPage() {
      return page;
   }

   public Resources(Class<T> resourceClass, Collection<T> resources, int numberOfPages, int page) {
      super();
      this.resourceClass = resourceClass;
      this.resources = resources;
      this.numberOfPages = numberOfPages;
      this.page = page;
   }
}
