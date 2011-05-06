package com.ss.scaffold.web;

import com.ss.scaffold.ScaffoldHidden;
import com.ss.scaffold.ScaffoldTemplate;

@ScaffoldTemplate("projectCard")
public class Card {

   public enum Status {
      NEW, WORKING, BLOCKED, DONE
   }

   @ScaffoldHidden
   private Long id;

   private String title;

   @ScaffoldHidden
   private Project project;

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
