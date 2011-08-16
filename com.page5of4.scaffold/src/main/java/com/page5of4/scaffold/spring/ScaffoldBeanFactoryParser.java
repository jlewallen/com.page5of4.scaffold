package com.page5of4.scaffold.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ScaffoldBeanFactoryParser implements BeanDefinitionParser {
   @Override
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ScaffoldBeanFactoryProcessor.class);
      parserContext.registerBeanComponent(new BeanComponentDefinition(factory.getBeanDefinition(), "scaffoldBeanFactoryProcessor"));
      return null;
   }
}
