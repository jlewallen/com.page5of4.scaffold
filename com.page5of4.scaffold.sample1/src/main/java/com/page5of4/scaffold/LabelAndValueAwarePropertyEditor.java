package com.page5of4.scaffold;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelAndValueAwarePropertyEditor extends PropertyEditorSupport {

   private static final Logger logger = LoggerFactory.getLogger(LabelAndValueAwarePropertyEditor.class);
   private Class<?> targetClass;

   public LabelAndValueAwarePropertyEditor(Class<?> targetClass) {
      this.targetClass = targetClass;
   }

   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      setValue(Finders.findAndInvokeFindById(targetClass, text));
   }

   @Override
   public String getAsText() {
      if(getValue() instanceof LabelAndValue) {
         return ((LabelAndValue)getValue()).getActualValue().toString();
      }
      return super.getAsText();
   }

}
