package com.FantasyBasketball.NBAFantasy.service;

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
    team.setRoster(new ArrayList<>());
    team.getRoster().add(player);
    player.setTeam(team);
    teamRepository.save(team);
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

    public Player createPlayer(Long leagueId, Long teamId, Player player){
        Team team = teamService.getTeamById(leagueId, teamId);
        player.setTeam(team);
        return playerRepository.save(player);
    }

    public Player getPlayerById(Long playerId){
       Player playerIWant = playerRepository.findById(playerId).orElse(null);
        return playerIWant;
    }

    public void editPlayer(Long leagueId, Long teamId, Long playerId, Player updatedPlayer){
        Team existingTeam = teamService.getTeamById(leagueId, teamId);
        existingTeam.getRoster().stream().filter(player -> player.getId().equals(playerId)).findFirst().ifPresent(player -> {
//            player.setName(updatedPlayer.getName());
            player.setPosition(updatedPlayer.getPosition());
            player.setNbaTeam(updatedPlayer.getNbaTeam());});
        teamRepository.save(existingTeam);
    }

    public void deletePlayer(Long leagueId, Long teamId, Long playerId){
        Team team = teamService.getTeamById(leagueId, teamId);
        team.getRoster().removeIf(player -> player.getId().equals(playerId));
        teamRepository.save(team);
        playerRepository.deleteById(playerId);
    }
    public List<Player> getPlayersByPosition(Long leagueId, Long teamId,String position){
        Team team = teamService.getTeamById(leagueId, teamId);
        List<Player> playersWithPosition = team.getRoster().stream().filter(player -> player.getPosition().equals(position)).toList();
        return playersWithPosition;
    }
}
