package com.page5of4.scaffold.sample1.web;

import javax.validation.constraints.NotNull;

import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.ScaffoldHidden;
import com.page5of4.scaffold.ScaffoldTemplate;

@ScaffoldTemplate("projectRelease")
public class Release {

   public enum Status {
      MILESTONE, BETA, ALPHA, STABLE
   }

   @ScaffoldHidden
   private Long id;

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