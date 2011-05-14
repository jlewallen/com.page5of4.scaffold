package com.page5of4.scaffold.spring;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestDataBinder;

public class ScaffoldRequestDataBinder extends ServletRequestDataBinder {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldRequestDataBinder.class);

   public ScaffoldRequestDataBinder(Object target, String objectName) {
      super(target, objectName);
   }

   @Override
   public void bind(ServletRequest request) {
      super.bind(request);
   }

   @Override
   protected void doBind(MutablePropertyValues mpvs) {
      super.doBind(mpvs);
   }

}
