package br.com.gamehub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    private List<Account> members = new ArrayList<>();

    @ManyToOne
    private Account leader;

    public Team(Long teamId) {
        this.id = teamId;
    }
}

