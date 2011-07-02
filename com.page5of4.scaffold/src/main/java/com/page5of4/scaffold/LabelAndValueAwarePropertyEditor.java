package com.page5of4.scaffold;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.scaffold.domain.Repository;

public class LabelAndValueAwarePropertyEditor extends PropertyEditorSupport {

   private static final Logger logger = LoggerFactory.getLogger(LabelAndValueAwarePropertyEditor.class);
   private final Repository repository;
   private final Class<?> targetClass;

   public LabelAndValueAwarePropertyEditor(Repository repository, Class<?> targetClass) {
      this.repository = repository;
      this.targetClass = targetClass;
   }

   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      setValue(repository.findById(targetClass, text));
   }

   @Override
   public String getAsText() {
      if(getValue() instanceof LabelAndValue) {
         return ((LabelAndValue)getValue()).getActualValue().toString();
      }
      return super.getAsText();
   }

}
