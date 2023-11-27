package com.FantasyBasketball.NBAFantasy.controller;

import com.FantasyBasketball.NBAFantasy.exceptions.ElementNotFoundException;
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

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    protected void verifyPlayer(Long leagueId, Long teamId, Long playerId) throws ElementNotFoundException {
        Optional<Player> player = Optional.ofNullable(playerService.getPlayerById(leagueId, teamId, playerId));
        if (player.isEmpty()) {
            throw new ElementNotFoundException("Player with id " + playerId + " not found");
        }
    }

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
        verifyPlayer(leagueId, teamId, playerId);
        Player player = playerService.getPlayerById(leagueId, teamId, playerId);
        logger.info("Player Gotten Successfully");
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @PutMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> editPlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId, @RequestBody Player updatedPlayer){
        logger.info("Request received: Editing Player");
        verifyPlayer(leagueId, teamId, playerId);
        playerService.editPlayer(leagueId, teamId, playerId, updatedPlayer);
        logger.info("Player Edited Successfully");
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId){
        logger.info("Request received: Deleting player");
        verifyPlayer(leagueId, teamId, playerId);
        playerService.deletePlayer(leagueId, teamId, playerId);
        logger.info("Player Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/byPosition")
    public ResponseEntity<List<Player>> getPlayersByPosition(@RequestParam(name = "leagueId") Long leagueId, @RequestParam(name = "teamId") Long teamId, @RequestParam(name = "position") String position){
        List<Player> players = playerService.getPlayersByPosition(leagueId,teamId,position);
        return ResponseEntity.ok(players);
    }
}
