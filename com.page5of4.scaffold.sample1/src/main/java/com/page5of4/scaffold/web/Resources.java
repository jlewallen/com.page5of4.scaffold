package com.page5of4.scaffold.web;

import java.util.Collection;

public class Resources<T extends Object> {
   private Collection<T> resources;
   private int page;
   private int pages;

   public Collection<T> getResources() {
      return resources;
   }

   public int getPage() {
      return page;
   }

   public int getPages() {
      return pages;
   }

   public Resources(Collection<T> resources, int page, int pages) {
      super();
      this.resources = resources;
      this.page = page;
      this.pages = pages;
   }
}