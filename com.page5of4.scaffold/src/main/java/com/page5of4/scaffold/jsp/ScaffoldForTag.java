package com.page5of4.scaffold.jsp;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.tiles.jsp.context.JspPrintWriterAdapter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.page5of4.scaffold.ScaffoldTagModel;
import com.page5of4.scaffold.TilesScaffoldProvider;
import com.page5of4.scaffold.metadata.ClassMetadata;

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

   public String[] getModes() {
      return new String[] { "scaffold" };
   }

   public ScaffoldForTag() {}

   @Override
   protected int doStartTagInternal() throws Exception {
      if(getObject() == null) throw new IllegalArgumentException("Object is required");
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      Object[] requestItems = new Object[] { getPageContext() };
      ScaffoldTagModel model = new ScaffoldTagModel(getModes(), getTemplatePrefix(), getFormPrefix(), getObject(), getPropertyName(), getClassMetadata(), null);
      try {
         getProvider().render(model, new JspPrintWriterAdapter(pageContext.getOut()), servletRequest, servletContext, requestItems);
      }
      catch(Exception e) {
         StringBuilder sb = new StringBuilder();
         sb.append("Error scaffolding '").append(model.determineObjectClass().getSimpleName());
         if(model.getPropertyName() != null) {
            sb.append(".").append(model.getPropertyName());
         }
         sb.append("' for ").append(StringUtils.join(getModes(), "/"));
         throw new RuntimeException(sb.toString(), e);
      }
      return EVAL_BODY_INCLUDE;
   }

   TilesScaffoldProvider getProvider() {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      WebApplicationContext wac = RequestContextUtils.getWebApplicationContext(servletRequest, servletContext);
      return wac.getBean(TilesScaffoldProvider.class);
   }
}
