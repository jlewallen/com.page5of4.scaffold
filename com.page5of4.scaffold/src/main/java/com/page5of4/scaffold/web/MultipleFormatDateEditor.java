package com.page5of4.scaffold.web;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Date;

public class MultipleFormatDateEditor extends PropertyEditorSupport {
   private final DateFormat[] readingDateFormats;
   private final DateFormat writingDateFormat;

   private final boolean allowEmpty;

   public MultipleFormatDateEditor(DateFormat[] readingDateFormats, DateFormat writingDateFormat, boolean allowEmpty) {
      this.readingDateFormats = readingDateFormats;
      this.writingDateFormat = writingDateFormat;
      this.allowEmpty = allowEmpty;
   }

   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      if(this.allowEmpty && !org.springframework.util.StringUtils.hasText(text)) {
         setValue(null);
      }
      else {
         for(DateFormat df : readingDateFormats) {
            ParsePosition position = new ParsePosition(0);
            Date parsed = df.parse(text, position);
            if(parsed != null) {
               setValue(parsed);
               break;
            }
         }
      }
   }

   @Override
   public String getAsText() {
      Date value = (Date)getValue();
      return(value != null ? this.writingDateFormat.format(value) : "");
   }

}