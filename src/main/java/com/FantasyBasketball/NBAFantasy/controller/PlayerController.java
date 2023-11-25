package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster")
    public ResponseEntity<?> createPlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @RequestBody Player player){
        logger.info("Request received: Creating Player");
        player = playerService.createPlayer(leagueId, teamId, player);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").buildAndExpand(player.getId()).toUri());
        logger.info("Player Created Successfully");
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    @GetMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster")
    public Iterable<Player> getRosterByTeam(@PathVariable Long leagueId, @PathVariable Long teamId){
        logger.info("Request received: Getting roster for this team");
        logger.info("Roster Gotten Successfully");
        return playerService.getRosterByTeam(leagueId, teamId);
    }

    @GetMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId){
        logger.info("Request received: Getting Player");
        Player player = playerService.getPlayerById(leagueId, teamId, playerId);
        logger.info("Player Gotten Successfully");
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @PutMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> editPlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId, @RequestBody Player updatedPlayer){
        logger.info("Request received: Editing Player");
        playerService.editPlayer(leagueId, teamId, playerId, updatedPlayer);
        logger.info("Player Edited Successfully");
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId){
        logger.info("Request received: Deleting player");
        playerService.deletePlayer(leagueId, teamId, playerId);
        logger.info("Player Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
