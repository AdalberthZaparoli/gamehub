package br.com.gamehub.controller;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Team;
import br.com.gamehub.exception.TeamAccessException;
import br.com.gamehub.service.AccountService;
import br.com.gamehub.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String showTeams() {
        return "team";
    }

    @PostMapping
    public String createTeam(@ModelAttribute Team team, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");
        team.setLeader(loggedUser);
        team.setMembers(List.of(loggedUser));
        teamService.createTeam(team);
        return "redirect:/dashboard";
    }

    @GetMapping("/join")
    public String showJoinTeamPage(Model model, HttpSession session) {
        List<Team> teams = teamService.getTeamsNotCompleted();
        model.addAttribute("teams", teams);
        return "join-team";
    }

    /**
     * Processa o pedido para juntar-se a uma equipe a partir de um botão.
     */
    @PostMapping("/join/{teamId}")
    public String joinTeam(
            @PathVariable Long teamId,
            @RequestParam(value = "accessCode", required = false) String accessCode,
            Model model,
            HttpSession session
    ) {
        try {
            Account loggedUser = (Account) session.getAttribute("loggedUser");

            teamService.addUserToTeam(teamId, accessCode, loggedUser);

            return "redirect:/teams/" + teamId;

        } catch (TeamAccessException e) {
            model.addAttribute("error", e.getMessage());

            List<Team> teams = teamService.getAllTeams();
            model.addAttribute("teams", teams);

            return "join-team";
        }
    }

    @GetMapping("/{teamId}")
    public String viewTeamDetails(@PathVariable Long teamId, Model model, HttpSession session) {
        Team team = teamService.getTeamById(teamId);
        Account loggedUser = (Account) session.getAttribute("loggedUser");

        boolean isLeader = teamService.isTeamLeader(teamId, loggedUser);

        model.addAttribute("team", team);
        model.addAttribute("isLeader", isLeader);

        return "team-details";
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam Long teamId, @RequestParam Long memberId, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");
        if (!teamService.isTeamLeader(teamId, loggedUser)) {
            throw new RuntimeException("Ação não permitida! Apenas o líder pode expulsar membros.");
        }

        teamService.removeMember(teamId, memberId);
        return "redirect:/teams/" + teamId;
    }

    @PostMapping("/leave/{teamId}")
    public String leaveTeam(@PathVariable Long teamId, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");
        teamService.removeUserFromTeam(teamId, loggedUser);
        return "redirect:/teams/join";
    }

    @GetMapping("/my-teams")
    public String getUserTeams(Model model, HttpSession session) {
        Account loggedUser = (Account) session.getAttribute("loggedUser");

        List<Team> teams = teamService.getTeamsForUser(loggedUser.getId());

        model.addAttribute("teams", teams);

        return "user-teams";
    }
}

