package com.page5of4.scaffold.metadata;

import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class InstancePropertyMetadata extends TemplateMetadata {

   private final PropertyMetadata propertyMetadata;
   private final Object targetObject;

   public PropertyMetadata getPropertyMetadata() {
      return propertyMetadata;
   }

   public Object getDisplayValue() {
      return propertyMetadata.getDisplayValue(targetObject);
   }

   public Object getTargetObject() {
      return targetObject;
   }

   public InstancePropertyMetadata(ClassMetadata classMetadata, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel, Object targetObject, PropertyMetadata propertyMetadata) {
      super(classMetadata, scaffoldViewModel, urlsViewModel);
      this.targetObject = targetObject;
      this.propertyMetadata = propertyMetadata;
   }

}
