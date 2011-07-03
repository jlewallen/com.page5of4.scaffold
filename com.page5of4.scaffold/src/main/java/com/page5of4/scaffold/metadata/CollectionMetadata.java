package com.page5of4.scaffold.metadata;

import java.util.List;

import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.web.ScaffoldViewModel;

public class CollectionMetadata extends TemplateMetadata {
   private static final String OBJECT_COLLECTION_NAME = "objects";
   private final List<?> targetCollection;
   private final List<TemplateMetadata> targetCollectionMetas;

   public List<?> getTargetCollection() {
      return targetCollection;
   }

   public List<TemplateMetadata> getTargetCollectionMetas() {
      return targetCollectionMetas;
   }

   public CollectionMetadata(ClassMetadata classMetadata, List<?> targetCollection, List<TemplateMetadata> targetCollectionMetas, ScaffoldViewModel scaffoldViewModel, UrlsViewModel urlsViewModel) {
      super(classMetadata, scaffoldViewModel, urlsViewModel);
      this.targetCollection = targetCollection;
      this.targetCollectionMetas = targetCollectionMetas;
   }

   @Override
   public String[] getCandidateTemplateNames() {
      return new String[] { OBJECT_COLLECTION_NAME };
   }
}
