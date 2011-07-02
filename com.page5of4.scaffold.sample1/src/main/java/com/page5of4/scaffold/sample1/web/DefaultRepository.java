package com.page5of4.scaffold.sample1.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.page5of4.scaffold.domain.jpa.JpaRepository;

@Service
public class DefaultRepository extends JpaRepository {

   @PostConstruct
   @Transactional
   public void initialize() {
      List<Project> projects = new ArrayList<Project>();
      projects.add(new Project(null, "ABC", "A", "A simple description of A", new Date(), new Date()));
      projects.add(new Project(null, "DEF", "B", "A simple description of B", new Date(), new Date()));
      projects.add(new Project(null, "XYZ", "C", "A simple description of C", new Date(), new Date()));
      projects.add(new Project(null, "QRS", "D", "A simple description of D", new Date(), new Date()));

      String[] cardTitles = new String[] { "Bug with '%s' pulling data and seeing stuff.", "Fix login for '%s'" };
      String[] releaseTitles = new String[] { "1.0", "2.0" };

      for(Project project : projects) {
         getEntityManager().persist(project);

         for(String title : cardTitles) {
            getEntityManager().persist(new Card(project, String.format(title, project.getName())));
         }

         for(String title : releaseTitles) {
            getEntityManager().persist(new Release(project));
         }
      }
   }

}
