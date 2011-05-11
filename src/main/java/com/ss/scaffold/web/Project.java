package com.ss.scaffold.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.ss.scaffold.ScaffoldHidden;
import com.ss.scaffold.ScaffoldTextArea;

public class Project {

   @ScaffoldHidden
   private Long id;

   @NotEmpty
   private String name;

   @NotEmpty
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

   public Project(Long id, String name) {
      super();
      this.id = id;
      this.name = name;
   }

   private static final Logger logger = LoggerFactory.getLogger(Project.class);

   public static Project findProject(String key) {
      logger.info("Finding Project: " + key);
      return null;
   }

   public static Collection<Project> findAll() {
      List<Project> all = new ArrayList<Project>();
      all.add(new Project(1L, "A"));
      all.add(new Project(2L, "B"));
      all.add(new Project(3L, "C"));
      all.add(new Project(4L, "D"));
      return all;
   }

}
