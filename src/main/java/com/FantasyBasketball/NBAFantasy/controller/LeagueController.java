package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.exceptions.ElementNotFoundException;
import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.service.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    private static final Logger logger = LoggerFactory.getLogger(LeagueController.class);

    protected void verifyLeague(Long leagueId) throws ElementNotFoundException {
        Optional<League> league = Optional.ofNullable(leagueService.getLeagueById(leagueId));
        if (league.isEmpty()) {
            throw new ElementNotFoundException("League with id " + leagueId + " not found");
        }
    }


    @GetMapping(value = "/leagues")
    public ResponseEntity<Iterable<League>> getAllLeagues(){
        logger.info("Request received: Getting all leagues");
        Iterable<League> allLeagues = leagueService.getAllLeagues();
        logger.info("All leagues gotten successfully");
        return new ResponseEntity<>(allLeagues, HttpStatus.OK);
    }

    @PostMapping(value = "/leagues")
    public ResponseEntity<?> createLeague(@RequestBody League league){
        logger.info("Request received: Creating league");
        league = leagueService.createLeague(league);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newLeagueUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(league.getId()).toUri();
        responseHeaders.setLocation(newLeagueUri);
        logger.info("League Created Successfully");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> getLeagueById(@PathVariable Long leagueId){
        logger.info("Request received: Getting league by Id");
        verifyLeague(leagueId);
        League l = leagueService.getLeagueById(leagueId);
        logger.info("Gotten League Successfully");
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @PutMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> updateLeague(@RequestBody League league, @PathVariable Long leagueId){
        logger.info("Request received: Updating league");
        verifyLeague(leagueId);
        leagueService.updateLeague(leagueId, league);
        logger.info("Updated League Successfully");
        return new ResponseEntity<>(league, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> deleteLeague(@PathVariable Long leagueId){
        logger.info("Request received: Deleting league");
        verifyLeague(leagueId);
        leagueService.deleteLeague(leagueId);
        logger.info("League Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
