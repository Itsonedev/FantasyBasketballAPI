package com.FantasyBasketball.NBAFantasy.service;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.repository.LeagueRepository;
import com.FantasyBasketball.NBAFantasy.repository.PlayerRepository;
import com.FantasyBasketball.NBAFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Iterable<Player> getRosterByTeam(Long leagueId, Long teamId){
        Team team = teamService.getTeamById(leagueId, teamId);
        return team.getRoster();
    }

    public Player createPlayer(Long leagueId, Long teamId, Player player){
        Team team = teamService.getTeamById(leagueId, teamId);
        player.setTeam(team);
        return playerRepository.save(player);
    }

    public Player getPlayerById(Long leagueId, Long teamId, Long playerId){
        Team team = teamService.getTeamById(leagueId, teamId);
        Optional<Player> playerIWant = team.getRoster().stream().filter(player -> player.getId().equals(playerId)).findFirst();
        return playerIWant.orElse(null);
    }

    public void editPlayer(Long leagueId, Long teamId, Long playerId, Player updatedPlayer){
        Team existingTeam = teamService.getTeamById(leagueId, teamId);
        existingTeam.getRoster().stream().filter(player -> player.getId().equals(playerId)).findFirst().ifPresent(player -> {
            player.setName(updatedPlayer.getName());
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
