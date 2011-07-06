package com.page5of4.scaffold.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;
import com.page5of4.scaffold.ui.NavigationViewModel;
import com.page5of4.scaffold.ui.NavigationViewModelFactory;
import com.page5of4.scaffold.web.ScaffoldController;

// @Controller
// @RequestMapping(value = "/admin/{resourceName}/")
public class ResourceAdmininistrationController<I extends Object, T extends Object> extends ScaffoldController<I, T> {

   private final NavigationViewModelFactory navigationViewModelFactory;

   @Autowired
   public ResourceAdmininistrationController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory viewModelFactory, Repository repository,
         NavigationViewModelFactory navigationViewModelFactory) {
      super(templateMetadataFactory, viewModelFactory, repository);
      this.navigationViewModelFactory = navigationViewModelFactory;
   }

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      // binder.registerCustomEditor(Card.class, new RepositoryAwarePropertyEditor(getRepository(), Card.class));
      // binder.registerCustomEditor(Project.class, new RepositoryAwarePropertyEditor(getRepository(), Project.class));
      binder.setIgnoreUnknownFields(false);
   }

   @ModelAttribute("navigationViewModel")
   public NavigationViewModel getNavigationViewModel() {
      return navigationViewModelFactory.createNavigationViewModel();
   }

}
