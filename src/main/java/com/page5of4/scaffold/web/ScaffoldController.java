package com.page5of4.scaffold.web;

import static org.jvnet.inflector.Noun.pluralOf;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.Finders;

@SuppressWarnings("unchecked")
public abstract class ScaffoldController<I extends Object, T extends Object> {

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int page) {
      return new ModelAndView(getIndexView(), "model", findResourcesOnPage(page));
   }

   @RequestMapping(value = "/new", method = RequestMethod.GET)
   public ModelAndView createForm() {
      T resource = BeanUtils.instantiate(getResourceClass());
      return new ModelAndView(getFormView(), "model", resource);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public ModelAndView show(@PathVariable I id) {
      T resource = findResource(id);
      return new ModelAndView(getShowView(), "model", resource);
   }

   @RequestMapping(value = "/{id}/form", method = RequestMethod.GET)
   public ModelAndView updateForm(@PathVariable I id) {
      T resource = findResource(id);
      return new ModelAndView(getFormView(), "model", resource);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.POST)
   public ModelAndView update(@PathVariable I id, @ModelAttribute("model") @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return new ModelAndView(getFormView(), "model", resource);
      }
      return update(id, resource, errors);
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView create(@ModelAttribute("model") @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return new ModelAndView(getFormView(), "model", resource);
      }
      return create(resource, errors);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public ModelAndView delete(@PathVariable I id) {
      return delete(id, findResource(id));
   }

   public Resources<T> findResourcesOnPage(int page) {
      Collection<T> all = (Collection<T>)Finders.findAndInvokeFindAll(getResourceClass());
      return new Resources<T>(all, 1, 1);
   }

   public T findResource(I id) {
      return (T)Finders.findAndInvokeFindById(getResourceClass(), id);
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

   public String getResourceCollectionName() {
      return pluralOf(getResourceName());
   }

   public ModelAndView update(I id, T resource, Errors errors) {
      return new ModelAndView(getShowView(), "model", resource);
   }

   public ModelAndView create(T resource, Errors errors) {
      return new ModelAndView(getShowView(), "model", resource);
   }

   public ModelAndView delete(I id, T resource) {
      return index(1);
   }
}
