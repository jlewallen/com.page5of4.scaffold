package com.ss.scaffold;

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
      Project model = new Project();
      model.setName("Super Cool Project");
      model.setDescription("A really nifty project that we're all working very hard on!");
      model.setStartingAt(new Date());
      return new ModelAndView("home", "model", model);
   }

}
