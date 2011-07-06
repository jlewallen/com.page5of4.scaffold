package com.page5of4.scaffold.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.configuration.Configurer;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class NavigationViewModelFactory {

   private final Configurer configurer;
   private final ScaffoldViewModelFactory factory;

   @Autowired
   public NavigationViewModelFactory(Configurer configurer, ScaffoldViewModelFactory factory) {
      super();
      this.configurer = configurer;
      this.factory = factory;
   }

   public NavigationViewModel createNavigationViewModel() {
      NavigationViewModel model = new NavigationViewModel();
      for(Class<?> klass : configurer.findAllScaffoldClasses()) {
         ScaffoldViewModel viewModel = factory.createScaffoldViewModel(klass);
         model.addItem(new NavigationItem(viewModel.getResourceTitle(), factory.createUrlsViewModel(viewModel, klass)));
      }
      return model;
   }

}
