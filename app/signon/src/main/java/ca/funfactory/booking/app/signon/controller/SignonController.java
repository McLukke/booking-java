package ca.funfactory.booking.app.signon.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignonController {

    @GetMapping("/signon/public")
    public List<String> publicHello() {
        return Arrays.asList("Hello", "World", "Public");
    }

    @GetMapping("/signon/private")
    public List<String> privateHello() {
        return Arrays.asList("Hello", "World", "Private");
    }

}
