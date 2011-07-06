package com.page5of4.scaffold.ui;

import com.page5of4.scaffold.UrlsViewModel;

public class NavigationItem {

   private String title;
   private UrlsViewModel urls;

   public String getTitle() {
      return title;
   }

   public UrlsViewModel getUrls() {
      return urls;
   }

   public NavigationItem(String title, UrlsViewModel urls) {
      super();
      this.title = title;
      this.urls = urls;
   }

}
