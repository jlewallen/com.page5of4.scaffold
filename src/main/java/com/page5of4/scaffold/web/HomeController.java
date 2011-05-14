package com.page5of4.scaffold.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.page5of4.scaffold.LabelAndValueAwarePropertyEditor;

@Controller
public class HomeController {

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(Project.class, new LabelAndValueAwarePropertyEditor(Project.class));
      binder.setIgnoreUnknownFields(false);
   }

   public static class ClientSideConstraint {}

   public static class PropertyConstraints {
      private String path;
      private String propertyName;
      private ClientSideConstraint[] constraints;

      public String getPath() {
         return path;
      }

      public String getPropertyName() {
         return propertyName;
      }

      public ClientSideConstraint[] getConstraints() {
         return constraints;
      }

      public PropertyConstraints(String path, String propertyName, ClientSideConstraint[] constraints) {
         super();
         this.path = path;
         this.propertyName = propertyName;
         this.constraints = constraints;
      }
   }

   public static class ClientSideConstraints {
      private List<PropertyConstraints> constraints = new ArrayList<PropertyConstraints>();

      public List<PropertyConstraints> getConstraints() {
         return constraints;
      }

      public ClientSideConstraints(Collection<PropertyConstraints> constraints) {
         super();
         this.constraints = new ArrayList<PropertyConstraints>(constraints);
      }
   }

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView home() {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      List<Class<?>> types = new ArrayList<Class<?>>();
      List<Class<?>> visited = new ArrayList<Class<?>>();
      types.add(HomeModel.class);
      while(!types.isEmpty()) {
         Class<?> klass = types.remove(0);
         visited.add(klass);
         BeanDescriptor descriptor = factory.getValidator().getConstraintsForClass(klass);
         for(PropertyDescriptor property : descriptor.getConstrainedProperties()) {
            if(property.isCascaded()) {
               if(!visited.contains(property.getElementClass())) {
                  types.add(property.getElementClass());
               }
            }
            for(ConstraintDescriptor<?> cd : property.getConstraintDescriptors()) {
               logger.info(String.format("%s %s %s", klass, cd.getAnnotation(), cd.getAttributes()));
               logger.info(String.format("%s", cd));
            }
         }
      }
      return new ModelAndView("home", "model", HomeModel.create());
   }

   @RequestMapping(value = "/", method = RequestMethod.POST)
   public ModelAndView save(@Valid @ModelAttribute("model") HomeModel model, Errors br, Model m) {
      ModelAndView mav = new ModelAndView("home", "model", model);
      if(br.hasErrors()) {
         logger.info("Errors");
         for(ObjectError error : br.getAllErrors()) {
            logger.info(String.format("OE %s %s %s", error.getObjectName(), error.getDefaultMessage(), error));
         }
         for(FieldError error : br.getFieldErrors()) {
            logger.info(String.format("FE %s %s %s", error.getObjectName(), error.getDefaultMessage(), error));
         }
      }
      return mav;
   }

}
