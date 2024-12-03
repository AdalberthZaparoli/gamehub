package br.com.gamehub.controller;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Match;
import br.com.gamehub.domain.Team;
import br.com.gamehub.service.MatchService;
import br.com.gamehub.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/history")
    public String getMatchHistory(Model model, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");

        List<Match> matches = matchService.getMatchHistory(loggedUser.getId());

        model.addAttribute("matches", matches);

        return "match-history";
    }

    @GetMapping("/find/{teamId}")
    public String findOpponent(@PathVariable Long teamId, Model model) {
        Team currentTeam = teamService.getTeamById(teamId);

        List<Team> availableTeams = matchService.findAvailableOpponents(teamId);

        model.addAttribute("currentTeam", currentTeam);
        model.addAttribute("availableTeams", availableTeams);
        return "find";
    }

    @PostMapping("/create")
    public String createMatch(@RequestParam Long teamId, @RequestParam Long opponentId) {
        matchService.createMatch(teamId, opponentId);
        return "redirect:/matches/history";
    }
}
