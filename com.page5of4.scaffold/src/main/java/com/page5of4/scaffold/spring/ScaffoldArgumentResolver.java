package com.page5of4.scaffold.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class ScaffoldArgumentResolver implements WebArgumentResolver {

   private static final Logger logger = LoggerFactory.getLogger(ScaffoldArgumentResolver.class);

   @Override
   public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
      ScaffoldModelAttribute annotation = methodParameter.getParameterAnnotation(ScaffoldModelAttribute.class);
      if(annotation == null) {
         return WebArgumentResolver.UNRESOLVED;
      }
      logger.info(String.format("Resolving: %s '%s' on (%s)", methodParameter.getParameterName(), methodParameter.getParameterType(), methodParameter.getMethod()));

      return WebArgumentResolver.UNRESOLVED;
   }
}
