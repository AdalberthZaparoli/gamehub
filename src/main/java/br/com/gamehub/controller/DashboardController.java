package br.com.gamehub.controller;

import br.com.gamehub.domain.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String showDashboard(Model model, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");
        model.addAttribute("user", loggedUser);
        return "dashboard";
    }
}

