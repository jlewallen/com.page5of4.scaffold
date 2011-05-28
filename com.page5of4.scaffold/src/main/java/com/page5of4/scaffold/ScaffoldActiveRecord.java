package com.page5of4.scaffold;

public interface ScaffoldActiveRecord<I extends Object, T> {

   I getId();

   void persist();

   T merge();

   void delete();

}
