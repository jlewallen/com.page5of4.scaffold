package com.page5of4.scaffold.metadata;

import static org.jvnet.inflector.Noun.pluralOf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.UrlsViewModel;
import com.page5of4.scaffold.configuration.Configurer;
import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.web.ScaffoldViewModel;

@Service
public class ScaffoldViewModelFactory {

   private final Configurer configuration;
   private final Repository repository;

   @Autowired
   public ScaffoldViewModelFactory(Configurer configuration, Repository repository) {
      super();
      this.configuration = configuration;
      this.repository = repository;
   }

   public ScaffoldViewModel createScaffoldViewModel(Class<?> objectClass) {
      String resourceName = StringUtils.capitaliseFirstLetter(objectClass.getSimpleName());
      String collectionName = pluralOf(resourceName);
      return new ScaffoldViewModel(resourceName, collectionName);
   }

   public UrlsViewModel createUrlsViewModel(ScaffoldViewModel scaffoldViewModel, Class<?> objectClass) {
      String collectionName = scaffoldViewModel.getCollectionName().toLowerCase();
      String indexUrl = encodeURL("/%s", collectionName);
      String createUrl = encodeURL("/%s", collectionName);
      String createFormUrl = encodeURL("/%s/form", collectionName);
      return new UrlsViewModel(indexUrl, createUrl, createFormUrl);
   }

   public UrlsViewModel createUrlsViewModel(ScaffoldViewModel scaffoldViewModel, Object targetObject) {
      Object id = getId(targetObject);
      if(id == null) {
         return createUrlsViewModel(scaffoldViewModel, targetObject.getClass());
      }
      String collectionName = scaffoldViewModel.getCollectionName().toLowerCase();
      String indexUrl = encodeURL("/%s", collectionName);
      String createUrl = encodeURL("/%s", collectionName);
      String createFormUrl = encodeURL("/%s/form", collectionName);
      String showUrl = encodeURL("/%s/%s", collectionName, id);
      String updateUrl = encodeURL("/%s/%s", collectionName, id);
      String updateFormUrl = encodeURL("/%s/%s/form", collectionName, id);
      String deleteUrl = encodeURL("/%s/%s", collectionName, id);
      return new UrlsViewModel(indexUrl, createUrl, createFormUrl, showUrl, updateUrl, updateFormUrl, deleteUrl);
   }

   private String encodeURL(String url, Object... parts) {
      String qualified = String.format(url, parts);
      if(configuration.getUrlPrefix() != null) {
         qualified = configuration.getUrlPrefix() + qualified;
      }
      return qualified;
   }

   private Object getId(Object object) {
      return repository.getIdOf(object);
   }

}
