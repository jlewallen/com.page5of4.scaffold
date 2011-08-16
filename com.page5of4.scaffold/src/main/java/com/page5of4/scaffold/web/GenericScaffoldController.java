package com.page5of4.scaffold.web;

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

public class GenericScaffoldController<I extends Object, T extends Object> extends AbstractScaffoldController {

   private final Class<?> primaryKeyClass;
   private final Class<?> resourceClass;

   @Autowired
   @SuppressWarnings("unchecked")
   public GenericScaffoldController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory scaffoldViewModelFactory, Repository repository, HttpServletRequest servletRequest) {
      super(templateMetadataFactory, scaffoldViewModelFactory, repository, servletRequest);
      ParameterizedType superClass = (ParameterizedType)getClass().getGenericSuperclass();
      this.primaryKeyClass = (Class<I>)(superClass.getActualTypeArguments()[0]);
      this.resourceClass = (Class<T>)(superClass.getActualTypeArguments()[1]);
   }

   @Override
   protected Class<?> getPrimaryKeyClass() {
      return primaryKeyClass;
   }

   @Override
   protected Class<?> getResourceClass() {
      return resourceClass;
   }

}
