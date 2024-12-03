package br.com.gamehub.repository;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Match;
import br.com.gamehub.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTeam1OrTeam2(Team team1, Team team2);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Match m WHERE (m.team1.id = :team1Id AND m.team2.id = :team2Id) " +
            "OR (m.team1.id = :team2Id AND m.team2.id = :team1Id)")
    boolean existsByTeams(@Param("team1Id") Long teamAId, @Param("team2Id") Long teamBId);

    List<Match> findAllByParticipantsContains(Account participant);

}
