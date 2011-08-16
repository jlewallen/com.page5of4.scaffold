package com.page5of4.scaffold.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ScaffoldBeanFactoryProcessor implements BeanFactoryPostProcessor {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldBeanFactoryProcessor.class);

   @Override
   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      /*
      Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Controller.class);
      for(Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
         logger.info("{} = {}", entry.getKey(), entry.getValue());
      }
      */
   }

}
