package com.page5of4.scaffold.web;

import static org.jvnet.inflector.Noun.pluralOf;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.page5of4.scaffold.RepositoryAwarePropertyEditor;
import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.configuration.Configurer;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;

@Controller
@RequestMapping(value = "/admin/{collectionName}")
public class DefaultScaffoldController extends AbstractScaffoldController {

   private final Configurer configurer;

   @Autowired
   public DefaultScaffoldController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory viewModelFactory, Repository repository, HttpServletRequest servletRequest,
         Configurer configurer) {
      super(templateMetadataFactory, viewModelFactory, repository, servletRequest);
      this.configurer = configurer;
   }

   @Override
   protected Class<?> getPrimaryKeyClass() {
      return Long.class;
   }

   @Override
   protected Class<?> getResourceClass() {
      String collectionName = getPathParameters().get("collectionName");
      for(Class<?> klass : configurer.findAllScaffoldClasses()) {
         String resourceName = StringUtils.capitaliseFirstLetter(klass.getSimpleName());
         if(pluralOf(resourceName).equalsIgnoreCase(collectionName)) {
            return klass;
         }
      }
      throw new RuntimeException("Unable to locate ResourceClass for " + collectionName);
   }

   @SuppressWarnings("unchecked")
   public Map<String, String> getPathParameters() {
      return (Map<String, String>)getServletRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
   }

   @Override
   public void initializeBinder(WebDataBinder binder) {
      for(Class<?> klass : configurer.findAllScaffoldClasses()) {
         binder.registerCustomEditor(klass, new RepositoryAwarePropertyEditor(getRepository(), klass));
      }
      SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
      binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(df, true));
      binder.setIgnoreUnknownFields(false);
      super.initializeBinder(binder);
   }
}
