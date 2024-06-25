package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cook/mypage")
    public String myPage() {
        return "myPage";
    }
	
    @GetMapping("/admin")
    public String adminPage() {
        return "adminIndex";
    }
}
