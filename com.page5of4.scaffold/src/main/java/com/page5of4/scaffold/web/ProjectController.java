package com.page5of4.scaffold.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController extends ScaffoldController<String, Project> {

   /*
   @RequestMapping(method = RequestMethod.POST)
   public Object save(@Valid @ModelAttribute("model") Project model, Errors errors) {
      return "redirect:/";
   }
   */

}
