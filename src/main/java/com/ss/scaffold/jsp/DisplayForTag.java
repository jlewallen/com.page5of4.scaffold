package com.ss.scaffold.jsp;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.tiles.Definition;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletTilesRequestContextFactory;
import org.apache.tiles.servlet.context.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public class DisplayForTag extends RequestContextAwareTag {

   private static final Logger logger = LoggerFactory.getLogger(DisplayForTag.class);
   private static final long serialVersionUID = 1L;

   public PageContext getPageContext() {
      return this.pageContext;
   }

   private Object object;

   public Object getObject() {
      return object;
   }

   public void setObject(Object object) {
      this.object = object;
   }

   private TilesRequestContextFactory tilesRequestContextFactory;

   @SuppressWarnings("deprecation")
   public DisplayForTag() {
      tilesRequestContextFactory = new ServletTilesRequestContextFactory();
      tilesRequestContextFactory.init(new HashMap<String, String>());
   }

   @Override
   protected int doStartTagInternal() throws Exception {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();

      ClassMetadata meta = ClassMetadata.create(object.getClass());
      for(PropertyMetadata property : meta.getProperties()) {
         logger.info(String.format("%s %s %s", property.getDisplayName(), property.getName(), StringUtils.join(property.getPossibleTemplateNames(), ", ")));
      }

      servletRequest.setAttribute("meta", meta);
      invokeTiles("object", servletRequest, servletContext);
      servletRequest.removeAttribute("meta");

      return 0;
   }

   private void invokeTiles(String definitionName, ServletRequest servletRequest, ServletContext servletContext) throws ServletException {
      BasicTilesContainer container = (BasicTilesContainer)ServletUtil.getCurrentContainer(servletRequest, servletContext);
      if(container == null) {
         throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
      }

      TilesRequestContext tilesRequestContext = tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), new Object[] { getPageContext() });
      Definition definition = container.getDefinitionsFactory().getDefinition(definitionName, tilesRequestContext);
      if(definition == null) {
         throw new ServletException("No such tiles definition: " + definitionName);
      }

      container.startContext(getPageContext()).inheritCascadedAttributes(definition);
      container.render(definitionName, getPageContext());
      container.endContext(getPageContext());
   }
}
