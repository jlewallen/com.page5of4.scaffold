package com.page5of4.scaffold.configuration;

import java.util.Collection;

public interface Configurer {

   void configure();

   Collection<Class<?>> findAllScaffoldClasses();

}
