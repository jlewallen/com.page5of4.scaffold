package com.page5of4.scaffold.sample1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.LabelAndValueAwarePropertyEditor;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;
import com.page5of4.scaffold.web.ScaffoldController;

@Controller
@RequestMapping(value = "/releases")
public class ReleaseController extends ScaffoldController<String, Release> {

   @Autowired
   public ReleaseController(TemplateMetadataFactory templateMetadataFactory, Repository repository) {
      super(templateMetadataFactory, repository);
   }

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(Card.class, new LabelAndValueAwarePropertyEditor(getRepository(), Card.class));
      binder.registerCustomEditor(Project.class, new LabelAndValueAwarePropertyEditor(getRepository(), Project.class));
      binder.setIgnoreUnknownFields(false);
   }
}
