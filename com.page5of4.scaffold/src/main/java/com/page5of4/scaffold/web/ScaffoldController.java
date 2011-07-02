package com.page5of4.scaffold.web;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

@SuppressWarnings("unchecked")
public abstract class ScaffoldController<I extends Object, T extends Object> {

   public static final String MODEL_KEY_NAME = "model";
   public static final String META_KEY_NAME = "meta";

   @Autowired
   private TemplateMetadataFactory templateMetadataFactory;

   @Autowired
   private HttpServletRequest servletRequest;

   @Autowired
   private Repository repository;

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int page) {
      return newModelAndView(getIndexView(), findResourcesOnPage(page));
   }

   @RequestMapping(value = "/form", method = RequestMethod.GET)
   public ModelAndView createForm() {
      T resource = BeanUtils.instantiate(getResourceClass());
      return newModelAndView(getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public ModelAndView show(@PathVariable I id) {
      T resource = findResource(id);
      return newModelAndView(getShowView(), resource, null);
   }

   @RequestMapping(value = "/{id}/form", method = RequestMethod.GET)
   public ModelAndView updateForm(@PathVariable I id) {
      T resource = findResource(id);
      return newModelAndView(getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.POST)
   public ModelAndView update(@PathVariable I id, @ModelAttribute(MODEL_KEY_NAME) @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return newModelAndView(getFormView(), resource, errors);
      }
      return update(id, resource, errors);
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView create(@ModelAttribute(MODEL_KEY_NAME) @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return newModelAndView(getFormView(), resource, errors);
      }
      return create(resource, errors);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public ModelAndView delete(@PathVariable I id) {
      return delete(id, findResource(id));
   }

   public Resources<T> findResourcesOnPage(int page) {
      Collection<T> all = (Collection<T>)repository.findAll(getResourceClass(), page);
      if(all == null) {
         all = new ArrayList<T>();
      }
      return new Resources<T>(getResourceClass(), all, 1, 1);
   }

   public T findResource(I id) {
      return (T)repository.findById(getResourceClass(), id);
   }

   public String getViewsRoot() {
      return getResourceName() + "/";
   }

   public String getShowView() {
      return getViewsRoot() + "show";
   }

   public String getIndexView() {
      return getViewsRoot() + "index";
   }

   public String getFormView() {
      return getViewsRoot() + "form";
   }

   public Class<T> getResourceClass() {
      ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
      return (Class<T>)((ParameterizedType)superclass).getActualTypeArguments()[1];
   }

   public String getResourceName() {
      return StringUtils.uncapitalize(getResourceClass().getSimpleName());
   }

   public ModelAndView update(I id, T resource, Errors errors) {
      return newModelAndView(getShowView(), resource, errors);
   }

   public ModelAndView create(T resource, Errors errors) {
      return newModelAndView(getShowView(), resource, errors);
   }

   private ModelAndView newModelAndView(String viewName, T resource, Errors errors) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resource);
      ScaffoldViewModel scaffoldViewModel = templateMetadataFactory.createScaffoldViewModel(getResourceClass());
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createTemplateMetadata(resource, scaffoldViewModel));
      return mav;
   }

   private ModelAndView newModelAndView(String viewName, Resources<T> resources) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resources);
      ScaffoldViewModel scaffoldViewModel = templateMetadataFactory.createScaffoldViewModel(getResourceClass());
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createTemplateMetadata(resources.getResourceClass(), new ArrayList<T>(resources.getResources()), scaffoldViewModel));
      return mav;
   }

   public ModelAndView delete(I id, T resource) {
      return index(1);
   }
}
