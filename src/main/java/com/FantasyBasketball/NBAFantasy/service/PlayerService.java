package com.FantasyBasketball.NBAFantasy.service;

import com.FantasyBasketball.NBAFantasy.exceptions.PlayerAlreadyOnATeamException;
import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.repository.LeagueRepository;
import com.FantasyBasketball.NBAFantasy.repository.PlayerRepository;
import com.FantasyBasketball.NBAFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private TeamService teamService;

    public List<Player> getAllAvailablePlayers(){
       return playerRepository.findAll();
    }
    @Transactional
    public void draftPlayerToTeam(Long playerId, Long teamId){
        Player player = playerRepository.findById(playerId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (player.getTeam() == null){
        team.setRoster(new ArrayList<>());
        team.getRoster().add(player);
        player.setTeam(team);
        teamRepository.save(team);
         }else {
        throw new PlayerAlreadyOnATeamException( player.getFirstName() + " "+ player.getLastName() +" is already on team: " + player.getTeam().getTeamName());
        }
    }
    @Transactional
    public void dropPlayerFromTeam(Long playerId, Long teamId){
        Player player = playerRepository.findById(playerId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (player.getTeam() == team){
            player.setTeam(null);
            team.getRoster().remove(player);
            teamRepository.save(team);
            playerRepository.save(player);
        }else {
            throw new PlayerAlreadyOnATeamException( player.getFirstName() + " "+ player.getLastName() +" is not currently on team: " + team.getTeamName());
        }
    }

    public List<Player> getPlayersByFirstName(String firstName) {
        return playerRepository.findByFirstName(firstName);
    }

    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findByPosition(position);
    }
    public List<Player> getPlayersByNbaTeam(String nbaTeam){
        return playerRepository.findByNbaTeam(nbaTeam);
    }

    public Iterable<Player> getRosterByTeam(Long leagueId, Long teamId){
        Team team = teamService.getTeamById(leagueId, teamId);
        return team.getRoster();
    }


    public Player getPlayerById(Long playerId){
       Player playerIWant = playerRepository.findById(playerId).orElse(null);
        return playerIWant;
    }




}
