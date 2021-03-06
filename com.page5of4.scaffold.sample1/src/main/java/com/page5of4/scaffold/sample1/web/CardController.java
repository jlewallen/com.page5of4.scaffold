package com.page5of4.scaffold.sample1.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.RepositoryAwarePropertyEditor;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ExternalMetadataFactory;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;
import com.page5of4.scaffold.web.GenericScaffoldController;

@Controller
@RequestMapping(value = "/admin/cards")
public class CardController extends GenericScaffoldController<String, Card> {

   @Autowired
   public CardController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory viewModelFactory, Repository repository, HttpServletRequest servletRequest,
         ExternalMetadataFactory externalMetadataFactory) {
      super(templateMetadataFactory, viewModelFactory, repository, servletRequest, externalMetadataFactory);
   }

   @Override
   @InitBinder
   public void initializeBinder(WebDataBinder binder) {
      binder.registerCustomEditor(Card.class, new RepositoryAwarePropertyEditor(getRepository(), Card.class));
      binder.registerCustomEditor(Project.class, new RepositoryAwarePropertyEditor(getRepository(), Project.class));
      binder.setIgnoreUnknownFields(false);
   }
}
