package com.page5of4.scaffold.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.ScaffoldHidden;
import com.page5of4.scaffold.ScaffoldTemplate;

@ScaffoldTemplate("projectCard")
public class Card {

   public enum Status {
      NEW, WORKING, BLOCKED, DONE
   }

   @ScaffoldHidden
   private Long id;

   @NotEmpty
   private String title;

   @NotNull
   @ScaffoldCollection
   private Project project;

   @NotNull
   private Status status;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

}
