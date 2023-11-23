package com.FantasyBasketball.NBAFantasy.service;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.repository.LeagueRepository;
import com.FantasyBasketball.NBAFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private LeagueService leagueService;

    public Iterable<Team> getAllTeamsByLeague(Long leagueId){
        return teamRepository.getAllTeamsByLeagueId(leagueId);
    }

    public Team createTeam(Long leagueId, Team team){
        League league = leagueService.getLeagueById(leagueId);
        team.setLeague(league);
        return teamRepository.save(team);
    }

    public Team getTeamById(Long leagueId, Long teamId){
        League league = leagueService.getLeagueById(leagueId);
        Optional<Team> teamIWant = league.getTeams().stream().filter(team -> team.getId().equals(teamId)).findFirst();
        return teamIWant.orElse(null);
    }

    public void editTeam(Long leagueId, Long teamId, Team updatedTeam){
        League existingLeague = leagueService.getLeagueById(leagueId);
        existingLeague.getTeams().stream().filter(team1 -> team1.getId().equals(teamId)).findFirst().ifPresent(team -> {
            team.setTeamName(updatedTeam.getTeamName());
            team.setTeamOwner(updatedTeam.getTeamOwner());});
        leagueRepository.save(existingLeague);
    }

    public void deleteTeam(Long leagueId, Long teamId){
        League league = leagueService.getLeagueById(leagueId);
        league.getTeams().removeIf(team -> team.getId().equals(teamId));

        leagueRepository.save(league);
        teamRepository.deleteById(teamId);
    }
}
