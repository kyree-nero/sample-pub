package sample.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import sample.web.controllers.BatchController;

@EnableWebMvc
@ComponentScan(basePackageClasses=BatchController.class)
public class WebConfiguration {

}
