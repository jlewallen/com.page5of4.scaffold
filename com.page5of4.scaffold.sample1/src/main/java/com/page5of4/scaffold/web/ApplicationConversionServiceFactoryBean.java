package com.page5of4.scaffold.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

import com.page5of4.scaffold.LabelAndValue;
import com.page5of4.scaffold.LabelAndValueModel;
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
      registry.addConverter(new Converter<Project, LabelAndValue>() {
         @Override
         public LabelAndValue convert(Project source) {
            return new LabelAndValueModel(source.getName(), source.getId(), source);
         }
      });
   }

   @Override
   public void afterPropertiesSet() {
      super.afterPropertiesSet();
      installLabelConverters(getObject());
   }

}
