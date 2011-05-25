package com.page5of4.scaffold.web;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.ScaffoldActiveRecord;

public class ScaffoldActiveRecordController<I extends Object, T extends ScaffoldActiveRecord<I>> extends ScaffoldController<I, T> {

   @Override
   public ModelAndView create(T resource, Errors errors) {
      resource.persist();
      return super.create(resource, errors);
   }

   @Override
   public ModelAndView delete(I id, T resource) {
      resource.delete();
      return super.delete(id, resource);
   }

   @Override
   public ModelAndView update(I id, T resource, Errors errors) {
      resource.merge();
      return super.update(id, resource, errors);
   }

}
