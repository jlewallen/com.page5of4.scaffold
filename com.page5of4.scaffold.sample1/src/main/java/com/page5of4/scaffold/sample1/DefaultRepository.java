package com.page5of4.scaffold.sample1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.page5of4.scaffold.StringUtils;
import com.page5of4.scaffold.domain.jpa.JpaRepository;
import com.page5of4.scaffold.sample1.web.Card;
import com.page5of4.scaffold.sample1.web.Project;
import com.page5of4.scaffold.sample1.web.Release;

@Service
public class DefaultRepository extends JpaRepository {

   @PostConstruct
   @Transactional
   public void initialize() {
      LoremIpsum random = new LoremIpsum();
      List<Project> projects = new ArrayList<Project>();
      for(short i = 0; i < 100; ++i) {
         String code = random.getRandomString(3).toUpperCase();
         String name = StringUtils.capitaliseFirstLetter(random.getRandomWords(6));
         projects.add(new Project(null, code, name, random.getParagraphs(2), new Date(), new Date()));
      }

      for(Project project : projects) {
         getEntityManager().persist(project);

         for(short j = 0; j < 4; ++j) {
            String title = StringUtils.capitaliseFirstLetter(random.getRandomWords(6));
            getEntityManager().persist(new Card(project, title));
         }

         for(short j = 0; j < 4; ++j) {
            getEntityManager().persist(new Release(project));
         }
      }
   }

}
