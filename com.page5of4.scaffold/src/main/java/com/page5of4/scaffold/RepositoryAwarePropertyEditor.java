package com.page5of4.scaffold;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.scaffold.domain.Repository;

public class RepositoryAwarePropertyEditor extends PropertyEditorSupport {
   private static final Logger logger = LoggerFactory.getLogger(RepositoryAwarePropertyEditor.class);
   private final Repository repository;
   private final Class<?> targetClass;

   public RepositoryAwarePropertyEditor(Repository repository, Class<?> targetClass) {
      this.repository = repository;
      this.targetClass = targetClass;
   }

   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      setValue(repository.findById(targetClass, text));
   }

   @Override
   public String getAsText() {
      // Allow conversion service to perform this conversion. We'll always just do toString otherwise.
      return null;
   }
}
