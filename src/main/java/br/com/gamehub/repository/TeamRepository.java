package br.com.gamehub.repository;

import br.com.gamehub.domain.Account;
import br.com.gamehub.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByMembersContains(Account member);
}

