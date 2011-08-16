package com.page5of4.scaffold.sample1.web;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.domain.Repository;
import com.page5of4.scaffold.metadata.ScaffoldViewModelFactory;
import com.page5of4.scaffold.metadata.TemplateMetadataFactory;
import com.page5of4.scaffold.web.GenericScaffoldController;

@Controller
@RequestMapping(value = "/admin/projects")
public class ProjectController extends GenericScaffoldController<String, Project> {

   @Autowired
   public ProjectController(TemplateMetadataFactory templateMetadataFactory, ScaffoldViewModelFactory scaffoldViewModelFactory, Repository repository, HttpServletRequest servletRequest) {
      super(templateMetadataFactory, scaffoldViewModelFactory, repository, servletRequest);
   }

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
      binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(df, true));
   }

}
