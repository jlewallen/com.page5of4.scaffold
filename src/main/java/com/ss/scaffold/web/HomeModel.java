package com.ss.scaffold.web;

import java.util.Date;

import javax.validation.Valid;

public class HomeModel {
   @Valid
   private Project project;
   @Valid
   private Card card;

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Card getCard() {
      return card;
   }

   public void setCard(Card card) {
      this.card = card;
   }

   public HomeModel() {
      this.project = new Project();
      this.card = new Card();
   }

   public HomeModel(Project project, Card card) {
      this.project = project;
      this.card = card;
   }

   public static HomeModel create() {
      Project project = new Project();
      project.setId(20L);
      project.setName("Super Cool Project");
      project.setDescription("A really nifty project that we're all working very hard on!");
      project.setStartingAt(new Date());
      Card card = new Card();
      card.setTitle("Fix all the bugs!");
      card.setStatus(Card.Status.BLOCKED);
      card.setProject(project);
      return new HomeModel(project, card);
   }
}
