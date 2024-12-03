package br.com.gamehub.service;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Team;
import br.com.gamehub.exception.TeamAccessException;
import br.com.gamehub.repository.AccountRepository;
import br.com.gamehub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public void addUserToTeam(Long teamId, String accessCode, Account account) {
        Team team = teamRepository.findById(teamId).get();
        var exists = team.getMembers().stream().filter(member -> member.getId().equals(account.getId())).findFirst();
        if (exists.isPresent()) {
            throw new TeamAccessException("Você já é membro desta equipe!");
        }
        team.getMembers().add(account);
        teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Team> getTeamsNotCompleted() {
        return teamRepository.findAll().stream().filter(team -> team.getMembers().size() < 5).toList();
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));
    }

    public void removeUserFromTeam(Long teamId, Account account) {
        Team team = getTeamById(teamId);
        var user = accountRepository.findById(account.getId()).get();
        team.getMembers().remove(user);
        teamRepository.save(team);
    }

    public boolean isTeamLeader(Long teamId, Account loggedUser) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        Account user = accountRepository.findById(loggedUser.getId()).get();

        return team.getLeader().equals(user);
    }

    public void removeMember(Long teamId, Long memberId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        team.getMembers().removeIf(member -> member.getId().equals(memberId));
        teamRepository.save(team);
    }

    public List<Team> getTeamsForUser(Long id) {
        Account user = accountRepository.findById(id).get();

        return teamRepository.findAllByMembersContains(user);
    }
}
