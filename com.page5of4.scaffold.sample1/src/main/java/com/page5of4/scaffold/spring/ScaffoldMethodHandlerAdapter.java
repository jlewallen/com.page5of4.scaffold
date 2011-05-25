package com.page5of4.scaffold.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ScaffoldMethodHandlerAdapter extends AnnotationMethodHandlerAdapter {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldMethodHandlerAdapter.class);

   @Override
   protected ServletRequestDataBinder createBinder(HttpServletRequest request, Object target, String objectName) throws Exception {
      return new ScaffoldRequestDataBinder(target, objectName);
   }

   @Override
   protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      return super.invokeHandlerMethod(request, response, handler);
   }

}
