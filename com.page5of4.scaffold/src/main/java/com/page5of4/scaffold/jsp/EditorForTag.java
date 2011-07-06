package com.page5of4.scaffold.jsp;

public class EditorForTag extends ScaffoldForTag {

   private static final long serialVersionUID = 1L;

   @Override
   public String[] getModes() {
      return new String[] { "editor", "scaffold" };
   }

}
