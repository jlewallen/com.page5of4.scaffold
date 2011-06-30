package com.page5of4.scaffold.web;

import java.util.Collection;

public class Resources<T extends Object> {
   private final Collection<T> resources;
   private final Class<T> resourceClass;
   private final int page;
   private final int pages;

   public Class<T> getResourceClass() {
      return resourceClass;
   }

   public Collection<T> getResources() {
      return resources;
   }

   public int getPage() {
      return page;
   }

   public int getPages() {
      return pages;
   }

   public Resources(Class<T> resourceClass, Collection<T> resources, int page, int pages) {
      super();
      this.resourceClass = resourceClass;
      this.resources = resources;
      this.page = page;
      this.pages = pages;
   }
}
