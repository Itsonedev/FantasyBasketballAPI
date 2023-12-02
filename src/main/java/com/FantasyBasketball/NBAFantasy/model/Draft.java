package com.FantasyBasketball.NBAFantasy.model;

import javax.persistence.*;
import java.util.List;
@Entity
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DRAFT_ID")
    private Long id;
    @OneToOne
    private League league;

    @OneToMany(mappedBy = "draft", cascade = CascadeType.ALL)
    private List<Player> players;

    public Draft() {
    }

    public Draft(Long id, List<Player> players) {
        this.id = id;
        this.players = players;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
