package com.ss.scaffold;

import java.beans.IntrospectionException;
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

public class ScaffoldTilesProvider {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldTilesProvider.class);
   private static final String META_VARIABLE_NAME = "meta";
   private TilesRequestContextFactory tilesRequestContextFactory;
   private MetadataResolver metadataResolver = new MetadataResolver();

   @SuppressWarnings("deprecation")
   public ScaffoldTilesProvider() {
      tilesRequestContextFactory = new ChainedTilesRequestContextFactory();
      tilesRequestContextFactory.init(new HashMap<String, String>());
   }

   public void render(String mode, String templatePrefix, String formPrefix, Object target, String propertyName, ServletRequest servletRequest, ServletContext servletContext, Object[] requestItems)
         throws IntrospectionException, ServletException {
      AbstractMetadata meta = metadataResolver.resolve(target, propertyName);
      List<String> convertedNames = new ArrayList<String>();
      for(String name : meta.getCandidateTemplateNames()) {
         convertedNames.add(StringUtils.lowercaseSeparated(name, "-"));
      }

      List<String> definitionNames = new ArrayList<String>();
      for(String path : getSearchPaths(mode, templatePrefix)) {
         for(String name : convertedNames) {
            String url = path + name;
            definitionNames.add(url);
         }
      }

      logger.info("Searching: {}", StringUtils.join(definitionNames, ", "));
      renderDefinition(definitionNames, meta, servletRequest, servletContext, requestItems);
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

   private void renderDefinition(Collection<String> definitionNames, AbstractMetadata meta, ServletRequest servletRequest, ServletContext servletContext, Object[] requestItems)
         throws ServletException {
      BasicTilesContainer container = (BasicTilesContainer)ServletUtil.getCurrentContainer(servletRequest, servletContext);
      if(container == null) {
         throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
      }

      TilesRequestContext tilesRequestContext = tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), requestItems);
      Definition definition = findDefinition(definitionNames, container, tilesRequestContext);

      logger.info("Rendering: {} = {}", definition.getName(), definition);

      Object existing = servletRequest.getAttribute(META_VARIABLE_NAME);
      servletRequest.setAttribute(META_VARIABLE_NAME, meta);
      container.startContext(requestItems).inheritCascadedAttributes(definition);
      container.render(definition.getName(), requestItems);
      container.endContext(requestItems);
      if(existing != null) {
         servletRequest.setAttribute(META_VARIABLE_NAME, existing);
      }
      else {
         servletRequest.removeAttribute(META_VARIABLE_NAME);
      }
   }

   private Definition findDefinition(Collection<String> definitionNames, BasicTilesContainer container, TilesRequestContext tilesRequestContext) throws ServletException {
      for(String definitionName : definitionNames) {
         Definition definition = container.getDefinitionsFactory().getDefinition(definitionName, tilesRequestContext);
         if(definition != null) {
            logger.info("Selected: {}", definitionName);
            return definition;
         }
      }
      throw new ServletException(String.format("No such tiles definition, searched: %s", StringUtils.join(definitionNames, ", ")));
   }

}
