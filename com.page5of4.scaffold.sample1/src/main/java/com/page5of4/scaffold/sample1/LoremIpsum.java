/*
 * Copyright (c) 2008 Sven Jacobs
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.page5of4.scaffold.sample1;

import java.util.Random;

/**
 * Simple lorem ipsum text generator.
 * <p>
 * Suitable for creating sample data for test cases and performance tests.
 * </p>
 * 
 * @author Sven Jacobs
 * @version 1.0
 */
public class LoremIpsum {
   public static final String LOREM_IPSUM =
         "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
   private String[] loremIpsumWords;

   public LoremIpsum() {
      this.loremIpsumWords = LOREM_IPSUM.split("\\s");
   }

   /**
    * Returns one sentence (50 words) of the lorem ipsum text.
    * 
    * @return 50 words of lorem ipsum text
    */
   public String getWords() {
      return getWords(50);
   }

   /**
    * Returns words from the lorem ipsum text.
    * 
    * @param amount
    *           Amount of words
    * @return Lorem ipsum text
    */
   public String getWords(int amount) {
      return getWords(amount, 0);
   }

   private Random random = new Random();

   public String getRandomWords(int amount) {
      StringBuilder lorem = new StringBuilder();

      for(int i = 0; i < amount; i++) {
         int word = random.nextInt(loremIpsumWords.length);
         lorem.append(loremIpsumWords[word]);

         if(i < amount - 1) {
            lorem.append(' ');
         }
      }

      return lorem.toString();
   }

   /**
    * Returns words from the lorem ipsum text.
    * 
    * @param amount
    *           Amount of words
    * @param startIndex
    *           Start index of word to begin with (must be >= 0 and < 50)
    * @return Lorem ipsum text
    * @throws IndexOutOfBoundsException
    *            If startIndex is < 0 or > 49
    */
   public String getWords(int amount, int startIndex) {
      if(startIndex < 0 || startIndex > 49) {
         throw new IndexOutOfBoundsException("startIndex must be >= 0 and < 50");
      }

      int word = startIndex;
      StringBuilder lorem = new StringBuilder();

      for(int i = 0; i < amount; i++) {
         if(word == 50) {
            word = 0;
         }

         lorem.append(loremIpsumWords[word]);

         if(i < amount - 1) {
            lorem.append(' ');
         }

         word++;
      }

      return lorem.toString();
   }

   /**
    * Returns two paragraphs of lorem ipsum.
    * 
    * @return Lorem ipsum paragraphs
    */
   public String getParagraphs() {
      return getParagraphs(2);
   }

   /**
    * Returns paragraphs of lorem ipsum.
    * 
    * @param amount
    *           Amount of paragraphs
    * @return Lorem ipsum paragraphs
    */
   public String getParagraphs(int amount) {
      StringBuilder lorem = new StringBuilder();

      for(int i = 0; i < amount; i++) {
         lorem.append(LOREM_IPSUM);

         if(i < amount - 1) {
            lorem.append("\n\n");
         }
      }

      return lorem.toString();
   }

   /**
    * 
    */
   public String getRandomString(int length) {
      StringBuffer out = new StringBuffer();

      char[] alphanumeric = getAlphanumeric();
      while(out.length() < length) {
         int idx = Math.abs((random.nextInt() % alphanumeric.length));
         out.append(alphanumeric[idx]);
      }
      return out.toString();

   }

   private char[] getAlphanumeric() {
      StringBuffer buf = new StringBuffer(128);
      for(int i = 48; i <= 57; i++)
         buf.append((char)i); // 0-9
      for(int i = 65; i <= 90; i++)
         buf.append((char)i); // A-Z
      for(int i = 97; i <= 122; i++)
         buf.append((char)i); // a-z
      return buf.toString().toCharArray();
   }

}
