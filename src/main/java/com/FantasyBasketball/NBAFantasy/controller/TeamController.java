package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping(value = "/leagues/{leagueId}/teams")
    public ResponseEntity<?> createTeam(@PathVariable Long leagueId, @RequestBody Team team){
        team = teamService.createTeam(leagueId,team);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(team.getId()).toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/leagues/{leagueId}/teams")
    public Iterable<Team> getAllTeams(@PathVariable Long leagueId){
        return teamService.getAllTeamsByLeague(leagueId);
    }

    @GetMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Long leagueId, @PathVariable Long teamId) {
        Team team = teamService.getTeamById(leagueId,teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PutMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> editTeamInfo(@PathVariable Long leagueId, @PathVariable Long teamId, @RequestBody Team updatedTeam){
        teamService.editTeam(leagueId, teamId, updatedTeam);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long leagueId, @PathVariable Long teamId){
        teamService.deleteTeam(leagueId, teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
