package com.page5of4.scaffold.sample1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrationDashboardController {

   @RequestMapping(value = "/admin", method = RequestMethod.GET)
   public ModelAndView home() {
      return new ModelAndView();
   }

}
