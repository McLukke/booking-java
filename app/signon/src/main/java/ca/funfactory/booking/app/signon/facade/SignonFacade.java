package ca.funfactory.booking.app.signon.facade;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class SignonFacade {
    @PreAuthorize("authenticated")
    public String sayHelloSecured() {
        return "Hello user.";
    }
}
