package com.page5of4.scaffold.sample1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.LabelAndValueAwarePropertyEditor;
import com.page5of4.scaffold.web.ScaffoldController;

@Controller
@RequestMapping(value = "/cards")
public class CardController extends ScaffoldController<String, Card> {

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(Card.class, new LabelAndValueAwarePropertyEditor(Card.class));
      binder.registerCustomEditor(Project.class, new LabelAndValueAwarePropertyEditor(Project.class));
      binder.setIgnoreUnknownFields(false);
   }

}
