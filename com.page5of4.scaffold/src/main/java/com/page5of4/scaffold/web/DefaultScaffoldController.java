package com.page5of4.scaffold.web;

import static org.jvnet.inflector.Noun.pluralOf;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

   private final Map<String, Class<?>> map = new ConcurrentHashMap<String, Class<?>>();
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
      if(map.isEmpty()) {
         for(Class<?> klass : configurer.findAllScaffoldClasses()) {
            String resourceName = StringUtils.capitaliseFirstLetter(klass.getSimpleName());
            String collectionName = pluralOf(resourceName).toLowerCase();
            map.put(collectionName, klass);
         }
      }
      String collectionName = getPathParameters().get("collectionName");
      if(map.containsKey(collectionName)) {
         return map.get(collectionName);
      }
      throw new RuntimeException("Unable to locate ResourceClass for " + collectionName);
   }

   @SuppressWarnings("unchecked")
   public Map<String, String> getPathParameters() {
      return (Map<String, String>)getServletRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
   }

   @Override
   @InitBinder
   public void initializeBinder(WebDataBinder binder) {
      for(Class<?> klass : configurer.findAllScaffoldClasses()) {
         binder.registerCustomEditor(klass, new RepositoryAwarePropertyEditor(getRepository(), klass));
      }
      SimpleDateFormat df1 = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
      SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
      binder.registerCustomEditor(java.util.Date.class, new MultipleFormatDateEditor(new DateFormat[] { df1, df2 }, df1, true));
      binder.setIgnoreUnknownFields(false);
      super.initializeBinder(binder);
   }

   public static class MultipleFormatDateEditor extends PropertyEditorSupport {
      private final DateFormat[] readingDateFormats;
      private final DateFormat writingDateFormat;

      private final boolean allowEmpty;

      public MultipleFormatDateEditor(DateFormat[] readingDateFormats, DateFormat writingDateFormat, boolean allowEmpty) {
         this.readingDateFormats = readingDateFormats;
         this.writingDateFormat = writingDateFormat;
         this.allowEmpty = allowEmpty;
      }

      @Override
      public void setAsText(String text) throws IllegalArgumentException {
         if(this.allowEmpty && !org.springframework.util.StringUtils.hasText(text)) {
            setValue(null);
         }
         else {
            for(DateFormat df : readingDateFormats) {
               ParsePosition position = new ParsePosition(0);
               Date parsed = df.parse(text, position);
               if(parsed != null) {
                  setValue(parsed);
                  break;
               }
            }
         }
      }

      @Override
      public String getAsText() {
         Date value = (Date)getValue();
         return(value != null ? this.writingDateFormat.format(value) : "");
      }

   }
}
