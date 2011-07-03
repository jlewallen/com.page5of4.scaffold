package com.page5of4.scaffold.metadata;

import org.springframework.util.Assert;

import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class InstancePropertyMetadata extends TemplateMetadata {

   private final PropertyMetadata propertyMetadata;
   private final Object targetObject;
   private final OneToManyPropertyMetadata oneToManyMetadata;
   private final ManyToOnePropertyMetadata manyToOneMetadata;

   public PropertyMetadata getPropertyMetadata() {
      return propertyMetadata;
   }

   public Object getDisplayValue() {
      return propertyMetadata.getDisplayValue(targetObject);
   }

   public Object getTargetObject() {
      return targetObject;
   }

   public boolean getIsOneToMany() {
      return oneToManyMetadata != null;
   }

   public OneToManyPropertyMetadata getOneToMany() {
      Assert.notNull(oneToManyMetadata);
      return oneToManyMetadata;
   }

   public boolean getIsManyToOne() {
      return manyToOneMetadata != null;
   }

   public ManyToOnePropertyMetadata getManyToOne() {
      Assert.notNull(manyToOneMetadata);
      return manyToOneMetadata;
   }

   public InstancePropertyMetadata(ClassMetadata classMetadata, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel, Object targetObject, PropertyMetadata propertyMetadata,
         OneToManyPropertyMetadata oneToManyMetadata, ManyToOnePropertyMetadata manyToOneMetadata) {
      super(classMetadata, scaffoldViewModel, urlsViewModel);
      this.targetObject = targetObject;
      this.propertyMetadata = propertyMetadata;
      this.oneToManyMetadata = oneToManyMetadata;
      this.manyToOneMetadata = manyToOneMetadata;
   }

   @Override
   public String[] getCandidateTemplateNames() {
      return getPropertyMetadata().getCandidateTemplateNames();
   }
}
