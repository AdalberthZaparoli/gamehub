package br.com.gamehub.service;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Match;
import br.com.gamehub.domain.Team;
import br.com.gamehub.repository.AccountRepository;
import br.com.gamehub.repository.MatchRepository;
import br.com.gamehub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Match> findMatchesByTeam(Long teamId) {
        return matchRepository.findByTeam1OrTeam2(new Team(teamId), new Team(teamId));
    }

    public List<Team> findAvailableOpponents(Long teamId) {
        List<Team> allTeams = teamRepository.findAll();
        List<Team> opponents = new ArrayList<>();

        for (Team team : allTeams) {
            if (!team.getId().equals(teamId) && !matchRepository.existsByTeams(teamId, team.getId())) {
                opponents.add(team);
            }
        }

        return opponents;
    }

    public List<Match> getMatchHistory(Long id) {
        Account user = accountRepository.findById(id).get();

        return matchRepository.findAllByParticipantsContains(user);
    }

    public void createMatch(Long teamId, Long opponentId) {
        Match match = new Match();
        var team1 = teamRepository.findById(teamId).orElseThrow();
        var team2 = teamRepository.findById(opponentId).orElseThrow();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setMatchDate(LocalDateTime.now());
        ArrayList<Account> participants = new ArrayList<>();
        participants.addAll(team1.getMembers());
        participants.addAll(team2.getMembers());
        match.setParticipants(participants);
        matchRepository.save(match);
    }
}
