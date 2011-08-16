package com.page5of4.scaffold.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ScaffoldBeanFactoryParser implements BeanDefinitionParser {
   @Override
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      /*
      RootBeanDefinition def = new RootBeanDefinition();
      def.setBeanClassName("");
      parserContext.registerBeanComponent(new BeanComponentDefinition(def, ""));

      BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ComponentFactoryBean.class);
      return factory.getBeanDefinition();
      */
      return null;
   }
}
