package com.page5of4.scaffold.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * Property name parser copied from {@link org.springframework.beans.BeanWrapperImpl} which for some reason had a
 * private implementation. If there's a publish one somewhere I'd love to know. Used to simplify some of the parsing.
 */
public class PropertyNameParser {

   /**
    * Path separator for nested properties. Follows normal Java conventions: getFoo().getBar() would be "foo.bar".
    */
   static String NESTED_PROPERTY_SEPARATOR = ".";
   static char NESTED_PROPERTY_SEPARATOR_CHAR = '.';

   /**
    * Marker that indicates the start of a property key for an indexed or mapped property like "person.addresses[0]".
    */
   static String PROPERTY_KEY_PREFIX = "[";
   static char PROPERTY_KEY_PREFIX_CHAR = '[';

   /**
    * Marker that indicates the end of a property key for an indexed or mapped property like "person.addresses[0]".
    */
   static String PROPERTY_KEY_SUFFIX = "]";
   static char PROPERTY_KEY_SUFFIX_CHAR = ']';

   public static PropertyTokenHolder getPropertyNameTokens(String propertyName) {
      String actualName = null;
      List<String> keys = new ArrayList<String>(2);
      int searchIndex = 0;
      while(searchIndex != -1) {
         int keyStart = propertyName.indexOf(PROPERTY_KEY_PREFIX, searchIndex);
         searchIndex = -1;
         if(keyStart != -1) {
            int keyEnd = propertyName.indexOf(PROPERTY_KEY_SUFFIX, keyStart + PROPERTY_KEY_PREFIX.length());
            if(keyEnd != -1) {
               if(actualName == null) {
                  actualName = propertyName.substring(0, keyStart);
               }
               String key = propertyName.substring(keyStart + PROPERTY_KEY_PREFIX.length(), keyEnd);
               if((key.startsWith("'") && key.endsWith("'")) || (key.startsWith("\"") && key.endsWith("\""))) {
                  key = key.substring(1, key.length() - 1);
               }
               keys.add(key);
               searchIndex = keyEnd + PROPERTY_KEY_SUFFIX.length();
            }
         }
      }
      actualName = (actualName != null ? actualName : propertyName);
      String canonicalName = actualName;
      String[] keysArray = null;
      if(!keys.isEmpty()) {
         canonicalName += PROPERTY_KEY_PREFIX + StringUtils.collectionToDelimitedString(keys, PROPERTY_KEY_SUFFIX + PROPERTY_KEY_PREFIX) + PROPERTY_KEY_SUFFIX;
         keysArray = StringUtils.toStringArray(keys);
      }
      return new PropertyTokenHolder(canonicalName, actualName, keysArray);
   }

}
