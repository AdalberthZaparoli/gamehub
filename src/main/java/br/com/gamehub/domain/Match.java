package br.com.gamehub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private LocalDateTime matchDate;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    @ManyToOne
    private Team winner;

    @ManyToMany
    private List<Account> participants = new ArrayList<>();
}
