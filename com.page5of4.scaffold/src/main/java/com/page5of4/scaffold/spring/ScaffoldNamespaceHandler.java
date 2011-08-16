package com.page5of4.scaffold.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ScaffoldNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      registerBeanDefinitionParser("annotation-driven", new ScaffoldBeanFactoryParser());
   }
}
