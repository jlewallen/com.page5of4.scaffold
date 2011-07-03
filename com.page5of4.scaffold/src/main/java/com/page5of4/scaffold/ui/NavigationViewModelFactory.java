package com.page5of4.scaffold.ui;

import org.springframework.stereotype.Service;

@Service
public class NavigationViewModelFactory {

   public NavigationViewModel createNavigationViewModel() {
      NavigationViewModel model = new NavigationViewModel();
      return model;
   }

}
