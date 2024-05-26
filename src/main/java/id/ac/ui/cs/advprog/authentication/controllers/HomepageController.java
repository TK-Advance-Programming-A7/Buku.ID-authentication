package id.ac.ui.cs.advprog.authentication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomepageController {
  
  @GetMapping("/")
  public ModelAndView homepage() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("homepage.html");
    return modelAndView;
  }
}