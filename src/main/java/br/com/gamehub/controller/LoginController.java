package br.com.gamehub.controller;

import br.com.gamehub.domain.Account;
import br.com.gamehub.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<Account> account = accountService.findByEmail(email);

        if (account.isPresent() && account.get().getPassword().equals(password)) {
            session.setAttribute("loggedUser", account.get());
            return "redirect:/dashboard";
        } else {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("error", "E-mail ou senha inválidos");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
