package com.page5of4.scaffold.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ExternalMetadataFactory;
import com.page5of4.scaffold.metadata.ExternalMetadataFactory.VisibleClassMetadata;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

public abstract class AbstractScaffoldController {

   public static final String MODEL_KEY_NAME = "model";
   public static final String META_KEY_NAME = "meta";

   private final TemplateMetadataFactory templateMetadataFactory;
   private final ExternalMetadataFactory externalMetadataFactory;
   private final Repository repository;
   private final ScaffoldViewModelFactory viewModelFactory;
   private final HttpServletRequest servletRequest;

   protected abstract Class<?> getPrimaryKeyClass();

   protected abstract Class<?> getResourceClass();

   protected ScaffoldViewModel getScaffoldViewModel() {
      return viewModelFactory.createScaffoldViewModel(getResourceClass());
   }

   protected HttpServletRequest getServletRequest() {
      return this.servletRequest;
   }

   protected Repository getRepository() {
      return repository;
   }

   public AbstractScaffoldController(TemplateMetadataFactory templateMetadataFactory, ExternalMetadataFactory externalMetadataFactory, ScaffoldViewModelFactory viewModelFactory,
         Repository repository, HttpServletRequest servletRequest) {
      this.templateMetadataFactory = templateMetadataFactory;
      this.externalMetadataFactory = externalMetadataFactory;
      this.repository = repository;
      this.viewModelFactory = viewModelFactory;
      this.servletRequest = servletRequest;
   }

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView index(@RequestParam(value = "page", defaultValue = "0") int page) {
      return newModelAndView(getScaffoldViewModel().getIndexView(), findResources(page));
   }

   @RequestMapping(value = "/form", method = RequestMethod.GET)
   public ModelAndView createForm() {
      Object resource = BeanUtils.instantiate(getResourceClass());
      return newModelAndView(getScaffoldViewModel().getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public ModelAndView show(@PathVariable String id) {
      Object resource = findResource(id);
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, null);
   }

   @RequestMapping(value = "/{id}/form", method = RequestMethod.GET)
   public ModelAndView updateForm(@PathVariable String id) {
      Object resource = findResource(id);
      return newModelAndView(getScaffoldViewModel().getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.POST)
   public ModelAndView update(@PathVariable String id, Model model) {
      Binding binding = createAndBind(model);
      Errors errors = binding.getErrors();
      Object resource = binding.getTargetObject();
      if(errors.hasErrors()) {
         return newModelAndView(getScaffoldViewModel().getFormView(), resource, errors);
      }
      repository.update(getResourceClass(), resource);
      model.asMap().clear();
      return update(id, resource, errors);
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView create(Model model) {
      Binding binding = createAndBind(model);
      Errors errors = binding.getErrors();
      Object resource = binding.getTargetObject();
      if(errors.hasErrors()) {
         return newModelAndView(getScaffoldViewModel().getFormView(), resource, errors);
      }
      repository.add(getResourceClass(), resource);
      model.asMap().clear();
      return create(resource, errors);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public ModelAndView delete(@PathVariable String id) {
      return delete(id, findResource(id));
   }

   @RequestMapping(value = "/meta", method = RequestMethod.GET)
   public @ResponseBody
   VisibleClassMetadata meta() {
      return externalMetadataFactory.createVisibleMetadata(getResourceClass());
   }

   protected int getPageSize() {
      return 20;
   }

   @SuppressWarnings("unchecked")
   protected Resources<Object> findResources(int page) {
      int pageSize = getPageSize();
      Collection<Object> all = (Collection<Object>)repository.findAll(getResourceClass(), page * pageSize, pageSize);
      long total = repository.countAll(getResourceClass());
      if(all == null) all = new ArrayList<Object>();
      return new Resources<Object>(getResourceClass(), all, (int)(total / pageSize), page);
   }

   protected Object findResource(Object id) {
      return repository.findById(getResourceClass(), id);
   }

   protected ModelAndView update(Object id, Object resource, Errors errors) {
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, errors);
   }

   protected ModelAndView create(Object resource, Errors errors) {
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, errors);
   }

   protected ModelAndView newModelAndView(String viewName, Object resource, Errors errors) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resource);
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createInstanceTemplateMetadata(resource, getScaffoldViewModel()));
      return mav;
   }

   protected ModelAndView newModelAndView(String viewName, Resources<?> resources) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resources);
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createCollectionTemplateMetadata(resources.getResourceClass(), new ArrayList<Object>(resources.getResources()), getScaffoldViewModel()));
      return mav;
   }

   protected ModelAndView delete(Object id, Object resource) {
      repository.delete(getResourceClass(), resource);
      return index(1);
   }

   public static class Binding {
      private final Errors errors;
      private final Object target;

      public Object getTargetObject() {
         return target;
      }

      public Errors getErrors() {
         return errors;
      }

      public Binding(Errors errors, Object target) {
         super();
         this.errors = errors;
         this.target = target;
      }
   }

   protected Binding createAndBind(Model model) {
      Object bindObject = BeanUtils.instantiate(getResourceClass());
      String objectName = MODEL_KEY_NAME;
      ServletRequestDataBinder binder = new ServletRequestDataBinder(bindObject, objectName);
      initializeBinder(binder);
      if(binder.getTarget() != null) {
         binder.bind(servletRequest);
      }
      BindingResult bindingResult = binder.getBindingResult();
      model.addAllAttributes(bindingResult.getModel());
      return new Binding(bindingResult, bindObject);
   }

   public void initializeBinder(WebDataBinder binder) {

   }

}
