package com.ss.scaffold.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.setIgnoreUnknownFields(false);
   }

   /*
   @ModelAttribute("model")
   public HomeModel findModel() {
      HomeModel model = HomeModel.create();
      logger.info("Model: {}", model);
      return model;
   }

   @ModelAttribute("project")
   public Project findProject() {
      Project project = HomeModel.create().getProject();
      logger.info("Project: {}", project);
      return project;
   }

   @ModelAttribute("projects")
   public Collection<Project> findProjects() {
      return Project.findAll();
   }
   */

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView home() {
      return new ModelAndView("home", "model", HomeModel.create());
   }

   @RequestMapping(value = "/", method = RequestMethod.POST)
   public ModelAndView save(@Valid @ModelAttribute("model") HomeModel model, Errors br, Model m) {
      ModelAndView mav = new ModelAndView("home", "model", model);
      if(br.hasErrors()) {
         logger.info("Errors");
         for(ObjectError error : br.getAllErrors()) {
            logger.info(String.format("OE %s %s %s", error.getObjectName(), error.getDefaultMessage(), error));
         }
         for(FieldError error : br.getFieldErrors()) {
            logger.info(String.format("FE %s %s %s", error.getObjectName(), error.getDefaultMessage(), error));
         }
      }
      return mav;
   }

}
