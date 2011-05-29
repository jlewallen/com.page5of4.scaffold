package com.page5of4.scaffold.sample1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.page5of4.scaffold.web.ScaffoldController;

@Controller
@RequestMapping(value = "/cards")
public class CardController extends ScaffoldController<String, Card> {

}
