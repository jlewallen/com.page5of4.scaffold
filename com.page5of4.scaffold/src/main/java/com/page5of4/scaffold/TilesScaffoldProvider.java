package com.page5of4.scaffold;

import java.beans.IntrospectionException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.tiles.Definition;
import org.apache.tiles.context.ChainedTilesRequestContextFactory;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadata;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class TilesScaffoldProvider {

   private static final Logger logger = LoggerFactory.getLogger(TilesScaffoldProvider.class);
   private static final String META_VARIABLE_NAME = "meta";
   private static final String MODEL_VARIABLE_NAME = "scaffold";
   private static final String INTERNAL_VIEW_PREFIX = "scaffold/";
   private static final String APPLICATION_VIEW_PREFIX = "application/";
   private final TilesRequestContextFactory tilesRequestContextFactory;
   private final TemplateMetadataFactory templateMetadataFactory;
   private final ScaffoldViewModelFactory scaffoldViewModelFactory;

   @Autowired
   @SuppressWarnings("deprecation")
   public TilesScaffoldProvider(ConversionService conversionService, TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory scaffoldViewModelFactory) {
      super();
      this.templateMetadataFactory = templateMetadataFactory;
      this.scaffoldViewModelFactory = scaffoldViewModelFactory;
      this.tilesRequestContextFactory = new ChainedTilesRequestContextFactory();
      this.tilesRequestContextFactory.init(new HashMap<String, String>());
   }

   public void render(ScaffoldTagModel model, PrintWriter writer, ServletRequest servletRequest, ServletContext servletContext, Object[] requestItems) throws IntrospectionException, ServletException {
      ScaffoldViewModel scaffoldViewModel = model.getScaffoldViewModel();
      if(scaffoldViewModel == null) {
         scaffoldViewModel = scaffoldViewModelFactory.createScaffoldViewModel(model.determineObjectClass());
      }
      TemplateMetadata templateMetadata =
            templateMetadataFactory.createTemplateMetadata(model.getTargetObject(), model.getObjectClass(), model.getTargetCollection(), model.getPropertyName(), scaffoldViewModel);

      List<String> convertedNames = new ArrayList<String>();
      for(String name : templateMetadata.getCandidateTemplateNames()) {
         convertedNames.add(StringUtils.lowercaseSeparated(name, "-"));
      }
      List<String> definitionNames = new ArrayList<String>();
      for(String mode : model.getModes()) {
         for(String path : getSearchPaths(new String[] { mode }, model.getTemplatePrefix())) {
            for(String name : convertedNames) {
               String url = path + name;
               if(!definitionNames.contains(url)) {
                  definitionNames.add(url);
               }
            }
         }
      }

      logger.trace("Searching: {}", StringUtils.join(definitionNames, ", "));
      renderDefinition(definitionNames, model, templateMetadata, writer, servletRequest, servletContext, requestItems);
   }

   private String[] getSearchPaths(String[] modes, String prefix) {
      List<String> paths = new ArrayList<String>();
      if(prefix != null) {
         if(!prefix.isEmpty() && !prefix.endsWith("/")) {
            prefix += "/";
         }
         paths.add(prefix);
      }
      for(String mode : modes) {
         paths.add(APPLICATION_VIEW_PREFIX + mode + "/");
      }
      for(String mode : modes) {
         paths.add(INTERNAL_VIEW_PREFIX + mode + "/");
      }
      return paths.toArray(new String[0]);
   }

   private void renderDefinition(Collection<String> definitionNames, ScaffoldTagModel model, TemplateMetadata templateMetadata, PrintWriter writer, ServletRequest servletRequest,
         ServletContext servletContext, Object[] requestItems) throws ServletException {
      BasicTilesContainer container = (BasicTilesContainer)ServletUtil.getCurrentContainer(servletRequest, servletContext);
      if(container == null) {
         throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
      }

      TilesRequestContext tilesRequestContext = tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), requestItems);
      Definition definition = findDefinition(definitionNames, container, tilesRequestContext);

      logger.trace("Rendering: {} = {}", definition.getName(), definition);
      writer.write(String.format("<!-- %s (%s) -->", definition.getName(), StringUtils.join(definitionNames, ", ")));

      Object existingModel = servletRequest.getAttribute(MODEL_VARIABLE_NAME);
      Object existingMeta = servletRequest.getAttribute(META_VARIABLE_NAME);
      servletRequest.setAttribute(MODEL_VARIABLE_NAME, model);
      servletRequest.setAttribute(META_VARIABLE_NAME, templateMetadata);

      container.startContext(requestItems).inheritCascadedAttributes(definition);
      container.render(definition.getName(), requestItems);
      container.endContext(requestItems);

      if(existingMeta != null) servletRequest.setAttribute(META_VARIABLE_NAME, existingMeta);
      else servletRequest.removeAttribute(META_VARIABLE_NAME);
      if(existingModel != null) servletRequest.setAttribute(MODEL_VARIABLE_NAME, existingModel);
      else servletRequest.removeAttribute(MODEL_VARIABLE_NAME);
   }

   private Definition findDefinition(Collection<String> definitionNames, BasicTilesContainer container, TilesRequestContext tilesRequestContext) throws ServletException {
      for(String definitionName : definitionNames) {
         Definition definition = container.getDefinitionsFactory().getDefinition(definitionName, tilesRequestContext);
         if(definition != null) {
            return definition;
         }
      }
      throw new ServletException(String.format("No such tiles definition, searched: %s", StringUtils.join(definitionNames, ", ")));
   }

}
