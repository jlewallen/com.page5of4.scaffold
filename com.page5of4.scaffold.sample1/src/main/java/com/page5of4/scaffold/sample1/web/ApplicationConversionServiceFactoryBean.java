package com.page5of4.scaffold.sample1.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

import com.page5of4.scaffold.spring.ScaffoldConversionServiceFactoryBean;

public class ApplicationConversionServiceFactoryBean extends ScaffoldConversionServiceFactoryBean {

   @Override
   protected void installFormatters(FormatterRegistry registry) {
      super.installFormatters(registry);
   }

   public void installLabelConverters(FormatterRegistry registry) {
      registry.addConverter(new Converter<Project, String>() {
         @Override
         public String convert(Project source) {
            return source.getName();
         }
      });
   }

   @Override
   public void afterPropertiesSet() {
      super.afterPropertiesSet();
      installLabelConverters(getObject());
   }

}
