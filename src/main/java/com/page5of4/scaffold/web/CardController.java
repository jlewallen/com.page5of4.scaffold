package com.page5of4.scaffold.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/cards")
public class CardController {

   @RequestMapping(method = RequestMethod.POST)
   public Object save(@Valid @ModelAttribute("model") Card model, Errors errors) {
      return "redirect:/";
   }

}
