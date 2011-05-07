package com.ss.scaffold.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.PropertyValue;
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
      List<PropertyValue> stripped = new ArrayList<PropertyValue>();
      for(PropertyValue pv : mpvs.getPropertyValues()) {
         String path = pv.getName();
         int pos;
         while((pos = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(path)) != -1) {
            String nestedProperty = path.substring(0, pos);
            String nestedPath = path.substring(pos + 1);
            logger.info(String.format("%s %s = %s", nestedProperty, nestedPath, pv.getValue()));

            path = nestedPath;
         }

         path = pv.getName();
         pos = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(path);
         if(pos != -1) {
            path = path.substring(pos + 1);
         }
         stripped.add(new PropertyValue(path, pv.getValue()));
      }
      super.doBind(new MutablePropertyValues(stripped));
   }
}