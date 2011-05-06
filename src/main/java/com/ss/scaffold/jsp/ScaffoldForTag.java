package com.ss.scaffold.jsp;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ss.scaffold.ClassMetadata;
import com.ss.scaffold.ScaffoldModel;
import com.ss.scaffold.ScaffoldTilesProvider;

public abstract class ScaffoldForTag extends RequestContextAwareTag {

   private static final long serialVersionUID = 1L;

   public PageContext getPageContext() {
      return this.pageContext;
   }

   private Object object;

   private String propertyName;

   private String templatePrefix;

   private String formPrefix;

   private ClassMetadata classMetadata;

   public Object getObject() {
      return object;
   }

   public void setObject(Object object) {
      this.object = object;
   }

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public void setClassMetadata(ClassMetadata classMetadata) {
      this.classMetadata = classMetadata;
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

   public ScaffoldForTag() {}

   @Override
   protected int doStartTagInternal() throws Exception {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      Object[] requestItems = new Object[] { getPageContext() };
      ScaffoldModel model = new ScaffoldModel(getMode(), getTemplatePrefix(), getFormPrefix(), getObject(), getPropertyName(), getClassMetadata());
      getProvider().render(model, getClassMetadata(), servletRequest, servletContext, requestItems);
      return EVAL_BODY_INCLUDE;
   }

   ScaffoldTilesProvider getProvider() {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      WebApplicationContext wac = RequestContextUtils.getWebApplicationContext(servletRequest, servletContext);
      return wac.getBean(ScaffoldTilesProvider.class);
   }
}
