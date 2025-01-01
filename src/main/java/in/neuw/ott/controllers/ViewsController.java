package in.neuw.ott.controllers;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewsController {

    @ResponseBody
    @GetMapping("/")
    public String landing(SecurityContext securityContext) {
        final String username = securityContext.getAuthentication().getName();
        return "Hello - "+username;
    }

}
