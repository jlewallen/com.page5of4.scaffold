package com.page5of4.scaffold.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.form.TagWriter;

public class ButtonTag extends HtmlEscapingAwareTag {

   private static final long serialVersionUID = 1L;

   private String cssClass;
   private String iconName;
   private String iconUrl;
   private String alt;
   private String text;
   private String href;
   private boolean submit;

   public String getHref() {
      return href;
   }

   public void setHref(String href) {
      this.href = href;
   }

   public String getCssClass() {
      return cssClass;
   }

   public void setCssClass(String cssClass) {
      this.cssClass = cssClass;
   }

   public String getIconName() {
      return iconName;
   }

   public void setIconName(String iconName) {
      this.iconName = iconName;
   }

   public String getIconUrl() {
      return iconUrl;
   }

   public void setIconUrl(String iconUrl) {
      this.iconUrl = iconUrl;
   }

   public String getAlt() {
      return alt;
   }

   public void setAlt(String alt) {
      this.alt = alt;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public boolean isSubmit() {
      return submit;
   }

   public void setSubmit(boolean submit) {
      this.submit = submit;
   }

   protected TagWriter createTagWriter() {
      return new TagWriter(this.pageContext);
   }

   @Override
   protected int doStartTagInternal() throws Exception {
      return writeTagContent(createTagWriter());
   }

   private String getUrl(String possiblyRelative) {
      if(possiblyRelative.startsWith("/")) {
         return possiblyRelative;
      }
      HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
      StringBuilder url = new StringBuilder();
      url.append(request.getContextPath());
      url.append("/");
      url.append(possiblyRelative);
      return url.toString();
   }

   protected int writeTagContent(TagWriter tagWriter) throws JspException {
      if(isSubmit()) {
         tagWriter.startTag("button");
         tagWriter.writeAttribute("type", "submit");
      }
      else {
         tagWriter.startTag("a");
         if(getHref() != null) {
            tagWriter.writeAttribute("href", href);
         }
         else {
            tagWriter.writeAttribute("href", "javascript:void(0)");
         }
      }
      if(getCssClass() != null) {
         tagWriter.writeAttribute("class", getCssClass());
      }
      else {
         tagWriter.writeAttribute("class", "button");
      }

      tagWriter.startTag("img");
      tagWriter.writeAttribute("src", getUrl(getIconUrl()));
      if(getAlt() != null) {
         tagWriter.writeAttribute("alt", getAlt());
      }
      if(getText() != null) {
         tagWriter.appendValue(getText());
      }
      tagWriter.endTag();
      tagWriter.endTag();

      return EVAL_BODY_INCLUDE;
   }

}
