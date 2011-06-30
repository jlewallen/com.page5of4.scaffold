package com.page5of4.scaffold.jsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.tiles.jsp.context.JspPrintWriterAdapter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.page5of4.scaffold.ClassMetadata;
import com.page5of4.scaffold.ScaffoldModel;
import com.page5of4.scaffold.TilesScaffoldProvider;

public class TableForTag extends RequestContextAwareTag {

   private static final long serialVersionUID = 1L;

   public PageContext getPageContext() {
      return this.pageContext;
   }

   private List<?> objects;

   private String templatePrefix;

   private ClassMetadata classMetadata;

   private Class<?> objectClass;

   public Collection<?> getObjects() {
      return objects;
   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void setObjects(Collection objects) {
      if(objects == null) {
         this.objects = new ArrayList<Object>();
      }
      else {
         this.objects = new ArrayList<Object>(objects);
      }
   }

   public ClassMetadata getClassMetadata() {
      return classMetadata;
   }

   public void setClassMetadata(ClassMetadata classMetadata) {
      this.classMetadata = classMetadata;
   }

   public String getTemplatePrefix() {
      return templatePrefix;
   }

   public void setTemplatePrefix(String templatePrefix) {
      this.templatePrefix = templatePrefix;
   }

   public void setObjectClass(Class<?> objectClass) {
      this.objectClass = objectClass;
   }

   public Class<?> getObjectClass() {
      return objectClass;
   }

   public String getMode() {
      return "table";
   }

   public TableForTag() {}

   @Override
   protected int doStartTagInternal() throws Exception {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      Object[] requestItems = new Object[] { getPageContext() };
      ScaffoldModel model = new ScaffoldModel(getMode(), getTemplatePrefix(), null, getObjectClass(), objects, null, getClassMetadata());
      getProvider().render(model, getClassMetadata(), new JspPrintWriterAdapter(pageContext.getOut()), servletRequest, servletContext, requestItems);
      return EVAL_BODY_INCLUDE;
   }

   TilesScaffoldProvider getProvider() {
      ServletRequest servletRequest = getPageContext().getRequest();
      ServletContext servletContext = getPageContext().getServletContext();
      WebApplicationContext wac = RequestContextUtils.getWebApplicationContext(servletRequest, servletContext);
      return wac.getBean(TilesScaffoldProvider.class);
   }

}
