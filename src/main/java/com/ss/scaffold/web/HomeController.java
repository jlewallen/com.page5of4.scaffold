package com.ss.scaffold.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView home() {
      logger.info("Welcome home!");
      return new ModelAndView("home", "model", HomeModel.create());
   }

   @RequestMapping(value = "/", method = RequestMethod.POST)
   public String save(HomeModel model, BindingResult br) {
      for(ObjectError error : br.getAllErrors()) {
         logger.info(String.format("Error %s %s %s", error.getObjectName(), error.getDefaultMessage(), error));
      }
      return "redirect:/";
   }

   public static class HomeModel {
      private Project project;
      private Card card;

      public Project getProject() {
         return project;
      }

      public void setProject(Project project) {
         this.project = project;
      }

      public Card getCard() {
         return card;
      }

      public void setCard(Card card) {
         this.card = card;
      }

      public HomeModel() {}

      public HomeModel(Project project, Card card) {
         this.project = project;
         this.card = card;
      }

      public static HomeModel create() {
         Project project = new Project();
         project.setId(20L);
         project.setName("Super Cool Project");
         project.setDescription("A really nifty project that we're all working very hard on!");
         project.setStartingAt(new Date());
         Card card = new Card();
         card.setTitle("Fix all the bugs!");
         card.setStatus(Card.Status.BLOCKED);
         card.setProject(project);
         return new HomeModel(project, card);
      }
   }

}
