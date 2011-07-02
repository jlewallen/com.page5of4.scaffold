package com.page5of4.scaffold.sample1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.LabelAndValueAwarePropertyEditor;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.web.ScaffoldController;

@Controller
@RequestMapping(value = "/cards")
public class CardController extends ScaffoldController<String, Card> {

   @Autowired
   private Repository repository;

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(Card.class, new LabelAndValueAwarePropertyEditor(repository, Card.class));
      binder.registerCustomEditor(Project.class, new LabelAndValueAwarePropertyEditor(repository, Project.class));
      binder.setIgnoreUnknownFields(false);
   }

}
