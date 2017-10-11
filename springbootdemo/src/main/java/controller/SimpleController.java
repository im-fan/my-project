package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SimpleController {

    public static void main(String[] args){
        SpringApplication.run(SimpleController.class,args);
    }

    @RequestMapping("/")
    @ResponseBody
    String out(){
        return "Hello World";
    }

}
