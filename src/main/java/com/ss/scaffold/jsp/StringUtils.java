package com.ss.scaffold.jsp;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

   public static String join(String[] parts, String separator) {
      return org.apache.commons.lang.StringUtils.join(parts, separator);
   }

   public static String join(Collection<String> parts, String separator) {
      return org.apache.commons.lang.StringUtils.join(parts, separator);
   }

   public static String interpolate(String text, Map<String, Object> values) {
      Pattern pattern = Pattern.compile("\\{(.+?)\\}");
      Matcher matcher = pattern.matcher(text);
      StringBuffer builder = new StringBuffer();
      while(matcher.find()) {
         String key = matcher.group(1);
         if(!values.containsKey(key)) {
            throw new RuntimeException("Missing Key: " + key + " in " + text);
         }
         Object value = values.get(key);
         if(value == null) {
            value = "NULL";
         }
         matcher.appendReplacement(builder, value.toString());
      }
      matcher.appendTail(builder);
      return builder.toString();
   }

   public static String lowercaseSeparated(String text, String separator) {
      Pattern pattern = Pattern.compile("([A-Z][a-z]+|[A-Z]+)");
      Vector<String> tokens = new Vector<String>();
      Matcher matcher = pattern.matcher(capitaliseFirstLetter(text));
      while(matcher.find()) {
         tokens.add(matcher.group().toLowerCase());
      }
      return join(tokens, separator);
   }

   public static String humanize(String text) {
      Pattern pattern = Pattern.compile("([A-Z]|[a-z])[a-z]*");
      Vector<String> tokens = new Vector<String>();
      Matcher matcher = pattern.matcher(text);
      String acronym = "";
      while(matcher.find()) {
         String found = matcher.group();
         if(found.matches("^[A-Z]$")) {
            acronym += found;
         }
         else {
            if(acronym.length() > 0) {
               // we have an acronym to add before we continue
               tokens.add(acronym);
               acronym = "";
            }
            tokens.add(found.toLowerCase());
         }
      }
      if(acronym.length() > 0) {
         tokens.add(acronym);
      }
      if(tokens.size() > 0) {
         String humanisedString = capitaliseFirstLetter(tokens.remove(0));
         for(String s : tokens) {
            humanisedString += " " + s;
         }
         return humanisedString;
      }

      return text;
   }

   public static String capitaliseFirstLetter(String str) {
      return str.substring(0, 1).toUpperCase() + str.substring(1);
   }
}
