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

@Service
public class TilesScaffoldProvider {

   private static final Logger logger = LoggerFactory.getLogger(TilesScaffoldProvider.class);
   private static final String META_VARIABLE_NAME = "meta";
   private static final String MODEL_VARIABLE_NAME = "scaffold";
   private TilesRequestContextFactory tilesRequestContextFactory;
   private MetadataResolver metadataResolver;
   private ConversionService conversionService;

   @Autowired
   @SuppressWarnings("deprecation")
   public TilesScaffoldProvider(ConversionService conversionService, MetadataResolver metadataResolver) {
      super();
      this.conversionService = conversionService;
      this.metadataResolver = metadataResolver;
      tilesRequestContextFactory = new ChainedTilesRequestContextFactory();
      tilesRequestContextFactory.init(new HashMap<String, String>());
   }

   public void render(ScaffoldModel model, ClassMetadata classMetadata, PrintWriter writer, ServletRequest servletRequest, ServletContext servletContext, Object[] requestItems)
         throws IntrospectionException, ServletException {
      AbstractMetadata meta = resolve(model);
      model.setMeta(meta);
      model.setTargetObject(model.getTargetObject());

      List<String> convertedNames = new ArrayList<String>();
      if(model.getTargetCollection() != null) {
         convertedNames.add("objects");
      }
      else {
         for(String name : meta.getCandidateTemplateNames()) {
            convertedNames.add(StringUtils.lowercaseSeparated(name, "-"));
         }
      }

      List<String> definitionNames = new ArrayList<String>();
      for(String path : getSearchPaths(model.getMode(), model.getTemplatePrefix())) {
         for(String name : convertedNames) {
            String url = path + name;
            definitionNames.add(url);
         }
      }

      logger.trace("Searching: {}", StringUtils.join(definitionNames, ", "));
      renderDefinition(definitionNames, model, writer, servletRequest, servletContext, requestItems);
   }

   private AbstractMetadata resolve(ScaffoldModel model) throws IntrospectionException {
      if(model.getClassMetadata() == null) {
         if(model.getObjectClass() != null) {
            model.setClassMetadata(metadataResolver.resolve(model.getObjectClass(), model.getTargetCollection(), model.getFormPrefix()));
         }
         else {
            model.setClassMetadata(metadataResolver.resolve(model.getTargetObject(), model.getFormPrefix()));
         }
      }
      if(model.getPropertyName() == null) {
         return model.getClassMetadata();
      }
      return model.getClassMetadata().findProperty(model.getPropertyName());
   }

   private String[] getSearchPaths(String mode, String prefix) {
      List<String> paths = new ArrayList<String>();
      if(prefix != null) {
         if(!prefix.isEmpty() && !prefix.endsWith("/")) {
            prefix += "/";
         }
         paths.add(prefix);
      }
      paths.add("scaffold/" + mode + "/");
      return paths.toArray(new String[0]);
   }

   private void renderDefinition(Collection<String> definitionNames, ScaffoldModel model, PrintWriter writer, ServletRequest servletRequest, ServletContext servletContext, Object[] requestItems)
         throws ServletException {
      BasicTilesContainer container = (BasicTilesContainer)ServletUtil.getCurrentContainer(servletRequest, servletContext);
      if(container == null) {
         throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
      }

      TilesRequestContext tilesRequestContext = tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), requestItems);
      Definition definition = findDefinition(definitionNames, container, tilesRequestContext);

      logger.trace("Rendering: {} = {}", definition.getName(), definition);
      writer.write(String.format("<!-- %s -->", definition.getName()));

      Object existingModel = servletRequest.getAttribute(MODEL_VARIABLE_NAME);
      Object existingMeta = servletRequest.getAttribute(META_VARIABLE_NAME);
      servletRequest.setAttribute(MODEL_VARIABLE_NAME, model);
      servletRequest.setAttribute(META_VARIABLE_NAME, model.getMeta());

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
