package com.page5of4.scaffold.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

public class GenericScaffoldController<I extends Object, T extends Object> extends ScaffoldController<I, T> {

   @Autowired
   public GenericScaffoldController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory scaffoldViewModelFactory, Repository repository) {
      super(templateMetadataFactory, scaffoldViewModelFactory, repository);
   }

}
