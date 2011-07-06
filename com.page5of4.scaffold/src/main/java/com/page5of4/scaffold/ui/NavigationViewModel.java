package com.page5of4.scaffold.ui;

import java.util.ArrayList;
import java.util.List;

public class NavigationViewModel {

   private List<NavigationItem> items = new ArrayList<NavigationItem>();

   public List<NavigationItem> getItems() {
      return items;
   }

   public void addItem(NavigationItem item) {
      items.add(item);
   }

}
