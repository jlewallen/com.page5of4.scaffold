package com.page5of4.scaffold.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ScaffoldModelAttribute {

   /**
    * @return
    */
   String prefix() default "";

}
