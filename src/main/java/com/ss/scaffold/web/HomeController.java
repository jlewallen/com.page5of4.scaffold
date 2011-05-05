package com.ss.scaffold.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView home() {
      logger.info("Welcome home!");
      return new ModelAndView("home", "model", new HomeModel());
   }

   public static class HomeModel {
      private Project project;
      private Card card;

      public Project getProject() {
         return project;
      }

      public Card getCard() {
         return card;
      }

      public HomeModel() {
         project = new Project();
         project.setName("Super Cool Project");
         project.setDescription("A really nifty project that we're all working very hard on!");
         project.setStartingAt(new Date());
         card = new Card();
         card.setTitle("Fix all the bugs!");
         card.setStatus(Card.Status.WORKING);
         card.setProject(project);
      }
   }

}
