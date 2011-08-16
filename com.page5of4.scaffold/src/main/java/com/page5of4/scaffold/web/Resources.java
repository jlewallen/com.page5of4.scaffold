package com.page5of4.scaffold.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Resources<T extends Object> {
   private final Collection<T> resources;
   private final Class<?> resourceClass;
   private final int numberOfPages;
   private final int page;

   public Class<?> getResourceClass() {
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

   public List<Page> getPages() {
      List<Page> pages = new ArrayList<Page>();
      for(int i = 0; i < numberOfPages; ++i) {
         pages.add(new Page(i, page == i));
      }
      return pages;
   }

   public Page getPreviousPage() {
      if(page <= 0) return null;
      return new Page(page - 1, false);
   }

   public Page getNextPage() {
      if(page >= numberOfPages - 1) return null;
      return new Page(page + 1, false);
   }

   public Resources(Class<?> resourceClass, Collection<T> resources, int numberOfPages, int page) {
      super();
      this.resourceClass = resourceClass;
      this.resources = resources;
      this.numberOfPages = numberOfPages;
      this.page = page;
   }

   public static class Page {
      private int number;
      private boolean selected;

      public int getNumber() {
         return number;
      }

      public boolean isSelected() {
         return selected;
      }

      public String getUrl() {
         return "?page=" + number;
      }

      public Page(int number, boolean selected) {
         super();
         this.number = number;
         this.selected = selected;
      }
   }
}
