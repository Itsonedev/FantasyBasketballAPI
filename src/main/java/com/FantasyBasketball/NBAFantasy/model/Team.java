package com.FantasyBasketball.NBAFantasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_OWNER")
    private String teamOwner;

    @Column(name = "TEAM_NAME")
    private String teamName;

//    @JsonIgnore
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> roster;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LEAGUE_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private League league;

    public Team() {
    }

    public Team(Long id, String teamOwner, String teamName, List<Player> roster, League league) {
        this.id = id;
        this.teamOwner = teamOwner;
        this.teamName = teamName;
        this.roster = roster;
        this.league = league;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getRoster() {
        return roster;
    }

    public void setRoster(List<Player> roster) {
        this.roster = roster;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(String teamOwner) {
        this.teamOwner = teamOwner;
    }
}
