package com.FantasyBasketball.NBAFantasy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.List;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAGUE_ID")
    private Long id;

    @Column(name = "LEAGUE_NAME")
    private String name;

    @OneToMany(mappedBy = "league")
    private List<Team> teams;

    @Column(name = "BUY_IN")
    private Double buyIn;

    @Column(name = "TOTAL_POT")
    private Double totalPot;
    public League() {
    }

    public League(Long id, String name, List<Team> teams, Double buyIn, Double totalPot) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.buyIn = buyIn;
        this.totalPot = totalPot;
    }

    public void updateTotalPot(){
        totalPot = buyIn * teams.size();
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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        updateTotalPot();
    }

    public Double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(Double buyIn) {
        this.buyIn = buyIn;
        updateTotalPot();
    }

    public Double getTotalPot() {
        return totalPot;
    }

    public void setTotalPot(Double totalPot) {
        this.totalPot = totalPot;
    }
}