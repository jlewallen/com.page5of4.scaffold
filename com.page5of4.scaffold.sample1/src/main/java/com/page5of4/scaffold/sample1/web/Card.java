package com.page5of4.scaffold.sample1.web;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.page5of4.scaffold.ScaffoldCollection;
import com.page5of4.scaffold.ScaffoldHidden;
import com.page5of4.scaffold.ScaffoldTemplate;

@Entity
@ScaffoldTemplate("projectCard")
public class Card {

   public enum Status {
      NEW, WORKING, BLOCKED, DONE
   }

   @Id
   @ScaffoldHidden
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotEmpty
   private String title;

   @NotNull
   @ManyToOne
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

   public Card() {

   }

   public Card(Project project, String title) {
      this.project = project;
      this.title = title;
      this.status = Status.NEW;
   }

}
