package com.ss.scaffold.web;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ss.scaffold.ScaffoldHidden;
import com.ss.scaffold.ScaffoldTextArea;

public class Project {

   @ScaffoldHidden
   private Long id;

   private String name;

   @ScaffoldTextArea
   private String description;

   @DateTimeFormat(pattern = "MM/dd/yyyy")
   private Date startingAt;

   @DateTimeFormat(pattern = "MM/dd/yyyy")
   private Date endingAt;

   private boolean started;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Date getStartingAt() {
      return startingAt;
   }

   public void setStartingAt(Date startingAt) {
      this.startingAt = startingAt;
   }

   public Date getEndingAt() {
      return endingAt;
   }

   public void setEndingAt(Date endingAt) {
      this.endingAt = endingAt;
   }

   public boolean isStarted() {
      return started;
   }

   public void setStarted(boolean started) {
      this.started = started;
   }

   public Project() {
      System.getenv();
   }

   public static Project findProject(String key) {
      throw new RuntimeException("What? " + key);
   }

}
