package com.page5of4.scaffold;

public interface ScaffoldActiveRecord<I extends Object> {

   I getId();

   void persist();

   void merge();

   void delete();

}
