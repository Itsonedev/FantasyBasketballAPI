package com.FantasyBasketball.NBAFantasy.model;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private Long id;

    @Column(name = "PLAYER_NAME")
    private String name;

    @Column(name = "PLAYER_POSITION")
    private String position;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Player() {
    }

    public Player(Long id, String name, String position, Team team) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
