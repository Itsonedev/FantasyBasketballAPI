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

    protected void verifyPlayer( Long playerId) throws ElementNotFoundException {
        Optional<Player> player = Optional.ofNullable(playerService.getPlayerById(playerId));
        if (player.isEmpty()) {
            throw new ElementNotFoundException("Player with id " + playerId + " not found");
        }
    }

    @GetMapping(value = "/players")
    public ResponseEntity<?> getAllPlayers(){
        logger.info("Request received: Getting all players");
        logger.info("All Players Gotten Successfully");
       return new ResponseEntity<>(playerService.getAllAvailablePlayers(),HttpStatus.OK);
    }

    @PostMapping(value = "/players/{playerId}/add-to-team/{teamId}")
    public ResponseEntity<?> draftPlayerToTeam(@PathVariable Long playerId, @PathVariable Long teamId){
        logger.info("Request received: Drafting player to team");
        playerService.draftPlayerToTeam(playerId, teamId);
        logger.info("Player added to team to Successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster")
    public Iterable<Player> getRosterByTeam(@PathVariable Long leagueId, @PathVariable Long teamId){
        logger.info("Request received: Getting roster for this team");
        logger.info("Roster Gotten Successfully");
        return playerService.getRosterByTeam(leagueId, teamId);
    }

    @GetMapping(value = "/players/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable Long playerId){
        logger.info("Request received: Getting Player");
        Player player = playerService.getPlayerById(playerId);
        logger.info("Player Gotten Successfully");
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @PutMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> editPlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId, @RequestBody Player updatedPlayer){
        logger.info("Request received: Editing Player");
        verifyPlayer(playerId);
        playerService.editPlayer(leagueId, teamId, playerId, updatedPlayer);
        logger.info("Player Edited Successfully");
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/leagues/{leagueId}/teams/{teamId}/roster/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long leagueId, @PathVariable Long teamId, @PathVariable Long playerId){
        logger.info("Request received: Deleting player");
        verifyPlayer(playerId);
        playerService.deletePlayer(leagueId, teamId, playerId);
        logger.info("Player Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/players/by-position")
    public ResponseEntity<List<Player>> getPlayersByPosition(@RequestParam String position){
        List<Player> players = playerService.getPlayersByPosition(position);
        return ResponseEntity.ok(players);
    }
    @GetMapping(value = "/players/by-nba-team")
    public ResponseEntity<List<Player>> getPlayersByNbaTeam(@RequestParam String nbaTeam){
        List<Player> players = playerService.getPlayersByNbaTeam(nbaTeam);
        return ResponseEntity.ok(players);
    }
    @GetMapping(value = "/players/by-first-name")
    public ResponseEntity<List<Player>> getPlayersByFirstName(@RequestParam String firstName){
        List<Player> players = playerService.getPlayersByFirstName(firstName);
        return ResponseEntity.ok(players);
    }
}
