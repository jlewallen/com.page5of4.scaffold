package com.page5of4.scaffold.sample1.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.page5of4.scaffold.ScaffoldHidden;
import com.page5of4.scaffold.ScaffoldTextArea;

public class Project {

   @ScaffoldHidden
   private Long id;

   @NotEmpty
   private String name;

   @Size(min = 3, max = 3)
   private String code;

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

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
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

   public Project(Long id, String name, String description, Date startingAt, Date endingAt) {
      super();
      this.id = id;
      this.name = name;
      this.description = description;
      this.startingAt = startingAt;
      this.endingAt = endingAt;
   }

   private static final Logger logger = LoggerFactory.getLogger(Project.class);

   public static Project findProject(String key) {
      if(key == null || key == "") {
         return null;
      }
      logger.info("Finding Project: " + key);
      for(Project p : findAll()) {
         if(p.getId().equals(Long.parseLong(key))) {
            return p;
         }
      }
      return null;
   }

   private static List<Project> all = new ArrayList<Project>();

   static {
      all.add(new Project(1L, "A", "A simple description of A", new Date(), new Date()));
      all.add(new Project(2L, "B", "A simple description of B", new Date(), new Date()));
      all.add(new Project(3L, "C", "A simple description of C", new Date(), new Date()));
      all.add(new Project(4L, "D", "A simple description of D", new Date(), new Date()));
   }

   public static Collection<Project> findAll() {
      return all;
   }

}
