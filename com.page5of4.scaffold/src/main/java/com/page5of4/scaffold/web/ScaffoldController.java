package com.page5of4.scaffold.web;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

@SuppressWarnings("unchecked")
public abstract class ScaffoldController<I extends Object, T extends Object> {

   public static final String MODEL_KEY_NAME = "model";
   public static final String META_KEY_NAME = "meta";

   private final Class<I> primaryKeyClass;
   private final Class<T> resourceClass;
   private final ScaffoldViewModel scaffoldViewModel;
   private final TemplateMetadataFactory templateMetadataFactory;
   private final ScaffoldViewModelFactory viewModelFactory;
   private final Repository repository;

   public Class<I> getPrimaryKeyClass() {
      return primaryKeyClass;
   }

   public Class<T> getResourceClass() {
      return resourceClass;
   }

   public ScaffoldViewModel getScaffoldViewModel() {
      return scaffoldViewModel;
   }

   public Repository getRepository() {
      return repository;
   }

   public ScaffoldController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory viewModelFactory, Repository repository) {
      this.templateMetadataFactory = templateMetadataFactory;
      this.viewModelFactory = viewModelFactory;
      this.repository = repository;
      ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
      this.resourceClass = (Class<T>)((ParameterizedType)superclass).getActualTypeArguments()[1];
      this.primaryKeyClass = (Class<I>)Object.class; // HACK
      this.scaffoldViewModel = viewModelFactory.createScaffoldViewModel(getResourceClass());
   }

   public ScaffoldController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory viewModelFactory, Repository repository, Class<I> primaryKeyClass, Class<T> resourceClass,
         ScaffoldViewModel scaffoldViewModel) {
      this.templateMetadataFactory = templateMetadataFactory;
      this.viewModelFactory = viewModelFactory;
      this.repository = repository;
      this.primaryKeyClass = primaryKeyClass;
      this.resourceClass = resourceClass;
      this.scaffoldViewModel = scaffoldViewModel;
   }

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView index(@RequestParam(value = "page", defaultValue = "0") int page) {
      return newModelAndView(getScaffoldViewModel().getIndexView(), findResources(page));
   }

   @RequestMapping(value = "/form", method = RequestMethod.GET)
   public ModelAndView createForm() {
      T resource = BeanUtils.instantiate(getResourceClass());
      return newModelAndView(getScaffoldViewModel().getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public ModelAndView show(@PathVariable I id) {
      T resource = findResource(id);
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, null);
   }

   @RequestMapping(value = "/{id}/form", method = RequestMethod.GET)
   public ModelAndView updateForm(@PathVariable I id) {
      T resource = findResource(id);
      return newModelAndView(getScaffoldViewModel().getFormView(), resource, null);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.POST)
   public ModelAndView update(@PathVariable I id, @ModelAttribute(MODEL_KEY_NAME) @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return newModelAndView(getScaffoldViewModel().getFormView(), resource, errors);
      }
      repository.update(getResourceClass(), resource);
      return update(id, resource, errors);
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView create(@ModelAttribute(MODEL_KEY_NAME) @Valid T resource, Errors errors, Model model) {
      if(errors.hasErrors()) {
         return newModelAndView(getScaffoldViewModel().getFormView(), resource, errors);
      }
      repository.add(getResourceClass(), resource);
      return create(resource, errors);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public ModelAndView delete(@PathVariable I id) {
      return delete(id, findResource(id));
   }

   @RequestMapping(value = "/meta", method = RequestMethod.GET)
   public ModelAndView meta() {
      return new ModelAndView("", "model", templateMetadataFactory.createVisibleMetadata(getResourceClass()));
   }

   public int getPageSize() {
      return 20;
   }

   public Resources<T> findResources(int page) {
      int pageSize = getPageSize();
      Collection<T> all = (Collection<T>)repository.findAll(getResourceClass(), page * pageSize, pageSize);
      long total = repository.countAll(getResourceClass());
      if(all == null) all = new ArrayList<T>();
      return new Resources<T>(getResourceClass(), all, (int)(total / pageSize), page);
   }

   public T findResource(I id) {
      return (T)repository.findById(getResourceClass(), id);
   }

   public ModelAndView update(I id, T resource, Errors errors) {
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, errors);
   }

   public ModelAndView create(T resource, Errors errors) {
      return newModelAndView(getScaffoldViewModel().getShowView(), resource, errors);
   }

   private ModelAndView newModelAndView(String viewName, T resource, Errors errors) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resource);
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createInstanceTemplateMetadata(resource, scaffoldViewModel));
      return mav;
   }

   private ModelAndView newModelAndView(String viewName, Resources<T> resources) {
      ModelAndView mav = new ModelAndView(viewName, MODEL_KEY_NAME, resources);
      mav.addObject(META_KEY_NAME, templateMetadataFactory.createCollectionTemplateMetadata(resources.getResourceClass(), new ArrayList<T>(resources.getResources()), scaffoldViewModel));
      return mav;
   }

   public ModelAndView delete(I id, T resource) {
      repository.delete(getResourceClass(), resource);
      return index(1);
   }
}
