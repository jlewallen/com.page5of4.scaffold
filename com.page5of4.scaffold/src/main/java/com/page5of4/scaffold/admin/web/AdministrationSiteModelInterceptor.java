package com.page5of4.scaffold.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.page5of4.scaffold.ui.NavigationViewModel;
import com.page5of4.scaffold.ui.NavigationViewModelFactory;

public class AdministrationSiteModelInterceptor extends HandlerInterceptorAdapter {

   private NavigationViewModelFactory navigationViewModelFactory;

   @Autowired
   public AdministrationSiteModelInterceptor(NavigationViewModelFactory navigationViewModelFactory) {
      super();
      this.navigationViewModelFactory = navigationViewModelFactory;
   }

   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      super.postHandle(request, response, handler, modelAndView);
      if(modelAndView != null) {
         NavigationViewModel model = navigationViewModelFactory.createNavigationViewModel();
         modelAndView.addObject("navigationViewModel", model);
      }
   }
}
