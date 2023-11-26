package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.exceptions.ElementNotFoundException;
import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    protected void verifyTeam(Long leagueId, Long teamId) throws ElementNotFoundException {
        Optional<Team> team = Optional.ofNullable(teamService.getTeamById(leagueId, teamId));
        if (team.isEmpty()) {
            throw new ElementNotFoundException("Team with id " + teamId + " not found");
        }
    }

    @PostMapping(value = "/leagues/{leagueId}/teams")
    public ResponseEntity<?> createTeam(@PathVariable Long leagueId, @RequestBody Team team){
        logger.info("Request received: Creating Team");
        team = teamService.createTeam(leagueId,team);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(team.getId()).toUri());
        logger.info("Team Created Successfully");
        return new ResponseEntity<>(team, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/leagues/{leagueId}/teams")
    public Iterable<Team> getAllTeams(@PathVariable Long leagueId){
        logger.info("Request received: Getting all teams for this league");
        logger.info("All Teams Gotten Successfully");
        return teamService.getAllTeamsByLeague(leagueId);
    }

    @GetMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Long leagueId, @PathVariable Long teamId) {
        logger.info("Request received: Getting team");
        verifyTeam(leagueId, teamId);
        Team team = teamService.getTeamById(leagueId,teamId);
        logger.info("Team Gotten Successfully");
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PutMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> editTeamInfo(@PathVariable Long leagueId, @PathVariable Long teamId, @RequestBody Team updatedTeam){
        logger.info("Request received: Editing Team");
        verifyTeam(leagueId, teamId);
        teamService.editTeam(leagueId, teamId, updatedTeam);
        logger.info("Team Edited Successfully");
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}/teams/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long leagueId, @PathVariable Long teamId){
        logger.info("Request received: Deleting team");
        verifyTeam(leagueId, teamId);
        teamService.deleteTeam(leagueId, teamId);
        logger.info("Team Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
