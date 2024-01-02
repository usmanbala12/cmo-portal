package org.cmo.cmoportal.security;

import org.cmo.cmoportal.cmoUser.SignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @ModelAttribute(name = "signupdto")
    public SignUpDto signUpDto() {
        return new SignUpDto();
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(SignUpDto signUpDto) {
        System.out.println(signUpDto);
        return null;
    }

}
