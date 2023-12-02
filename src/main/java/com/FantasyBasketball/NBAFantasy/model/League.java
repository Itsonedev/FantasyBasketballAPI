package com.FantasyBasketball.NBAFantasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAGUE_ID")
    private Long id;

    @Column(name = "COMMISSIONER")
    private String commissioner;

    @Column(name = "LEAGUE_NAME")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Team> teams;
    @JsonIgnore
    @OneToOne
    private Draft draft;

    @Column(name = "BUY_IN")
    private Double buyIn;

    @Column(name = "TOTAL_POT")
    private Double totalPot = 0.0;
    public League() {
    }

    public League(Long id, String commissioner, String name, List<Team> teams, Draft draft, Double buyIn, Double totalPot) {
        this.id = id;
        this.commissioner = commissioner;
        this.name = name;
        this.teams = teams;
        this.draft = draft;
        this.buyIn = buyIn;
        this.totalPot = totalPot;
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
    }

    public Double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(Double buyIn) {
        this.buyIn = buyIn;
    }

    public Double getTotalPot() {
        return totalPot;
    }

    public void setTotalPot(Double totalPot) {
        this.totalPot = totalPot;
    }
    public void addTeam(Team team) {
        teams.add(team);
        totalPot += buyIn;
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        totalPot -= buyIn;
    }

    public String getCommissioner() {
        return commissioner;
    }

    public void setCommissioner(String commissioner) {
        this.commissioner = commissioner;
    }

    public Draft getDraft() {
        return draft;
    }

    public void setDraft(Draft draft) {
        this.draft = draft;
    }
}
