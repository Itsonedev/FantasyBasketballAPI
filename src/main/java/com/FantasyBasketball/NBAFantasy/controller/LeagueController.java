package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.service.LeagueService;
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

    @GetMapping(value = "/leagues")
    public ResponseEntity<Iterable<League>> getAllLeagues(){
        Iterable<League> allLeagues = leagueService.getAllLeagues();
        return new ResponseEntity<>(allLeagues, HttpStatus.OK);
    }

    @PostMapping(value = "/leagues")
    public ResponseEntity<?> createLeague(@RequestBody League league){
        league = leagueService.createLeague(league);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newLeagueUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(league.getId()).toUri();
        responseHeaders.setLocation(newLeagueUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> getLeagueById(@PathVariable Long leagueId){
        League l = leagueService.getLeagueById(leagueId);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @PutMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> updateLeague(@RequestBody League league, @PathVariable Long leagueId){
        leagueService.updateLeague(leagueId, league);
        return new ResponseEntity<>(league, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}")
    public ResponseEntity<?> deleteLeague(@PathVariable Long leagueId){
        leagueService.deleteLeague(leagueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
