package com.ss.scaffold.jsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.tiles.Definition;
import org.apache.tiles.context.ChainedTilesRequestContextFactory;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public abstract class ScaffoldForTag extends RequestContextAwareTag {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldForTag.class);
   private static final long serialVersionUID = 1L;

   private TilesRequestContextFactory tilesRequestContextFactory;
   private MetadataResolver metadataResolver = new MetadataResolver();

   public PageContext getPageContext() {
      return this.pageContext;
   }

   private Object object;

   private String propertyName;

   private String prefix;

   public Object getObject() {
      return object;
   }

   public void setObject(Object object) {
      this.object = object;
   }

   public String getPropertyName() {
      return propertyName;
   }

   public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
   }

   public String getPrefix() {
      return prefix;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public String getMode() {
      return "scaffold";
   }

   @SuppressWarnings("deprecation")
   public ScaffoldForTag() {
      tilesRequestContextFactory = new ChainedTilesRequestContextFactory();
      tilesRequestContextFactory.init(new HashMap<String, String>());
   }

   @Override
   protected int doStartTagInternal() throws Exception {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();

      String prefix = getPrefix() != null ? getPrefix() : "";
      if(!prefix.isEmpty() && !prefix.endsWith("/")) {
         prefix += "/";
      }

      AbstractMetadata meta = metadataResolver.resolve(getObject(), getPropertyName());
      List<String> convertedNames = new ArrayList<String>();
      for(String name : meta.getCandidateTemplateNames()) {
         String converted = StringUtils.lowercaseSeparated(name, "-");
         String url = prefix + converted;
         convertedNames.add(url);
      }

      List<String> definitionNames = new ArrayList<String>();
      for(String path : new String[] { prefix, "/scaffold/" }) {
         for(String name : convertedNames) {
            String url = path + name;
            definitionNames.add(url);
            logger.info("Searching: {}", url);
         }
      }

      servletRequest.setAttribute("meta", meta);
      renderDefinition(definitionNames, servletRequest, servletContext);
      servletRequest.removeAttribute("meta");

      return 0;
   }

   private void renderDefinition(Collection<String> definitionNames, ServletRequest servletRequest, ServletContext servletContext) throws ServletException {
      BasicTilesContainer container = (BasicTilesContainer)ServletUtil.getCurrentContainer(servletRequest, servletContext);
      if(container == null) {
         throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
      }

      TilesRequestContext tilesRequestContext = tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), new Object[] { getPageContext() });
      Definition definition = findDefinition(definitionNames, container, tilesRequestContext);

      container.render(definition.getName(), getPageContext());
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
