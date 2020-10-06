package io.nightlyside.enstabretagne.ctfa;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello-world")
    public String index() {
        return "Hello world!";
    }

}