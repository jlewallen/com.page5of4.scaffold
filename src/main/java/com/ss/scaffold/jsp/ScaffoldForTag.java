package com.ss.scaffold.jsp;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ss.scaffold.ScaffoldTilesProvider;

public abstract class ScaffoldForTag extends RequestContextAwareTag {

   private static final long serialVersionUID = 1L;
   private static ScaffoldTilesProvider provider = new ScaffoldTilesProvider();

   public PageContext getPageContext() {
      return this.pageContext;
   }

   private Object object;

   private String propertyName;

   private String templatePrefix;

   private String formPrefix;

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

   public String getTemplatePrefix() {
      return templatePrefix;
   }

   public void setTemplatePrefix(String templatePrefix) {
      this.templatePrefix = templatePrefix;
   }

   public String getFormPrefix() {
      return formPrefix;
   }

   public void setFormPrefix(String formPrefix) {
      this.formPrefix = formPrefix;
   }

   public String getMode() {
      return "scaffold";
   }

   @Override
   protected int doStartTagInternal() throws Exception {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      Object[] requestItems = new Object[] { getPageContext() };
      provider.render(getMode(), getTemplatePrefix(), getFormPrefix(), getObject(), getPropertyName(), servletRequest, servletContext, requestItems);
      return 0;
   }
}
