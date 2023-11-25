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
        Team savedTeam = teamRepository.save(team);
        league.addTeam(savedTeam);

        leagueService.updateLeague(leagueId,league);
        return savedTeam;
    }

    public Team getTeamById(Long leagueId, Long teamId){
        League league = leagueService.getLeagueById(leagueId);
        Optional<Team> teamIWant = league.getTeams().stream().filter(team -> team.getId().equals(teamId)).findFirst();
        return teamIWant.orElse(null);
    }

    public void editTeam(Long leagueId, Long teamId, Team updatedTeam){
        League existingLeague = leagueService.getLeagueById(leagueId);
        existingLeague.getTeams().stream().filter(team1 -> team1.getId().equals(teamId)).findFirst().ifPresent(team -> {
            if (updatedTeam.getTeamName() != null) {
                team.setTeamName(updatedTeam.getTeamName());
            }
            team.setTeamOwner(updatedTeam.getTeamOwner());});
        leagueRepository.save(existingLeague);
    }

    public void deleteTeam(Long leagueId, Long teamId){
        League league = leagueService.getLeagueById(leagueId);
        Team team = teamRepository.findById(teamId).orElse(null);
        league.removeTeam(team);
        leagueService.updateLeague(leagueId, league);
        teamRepository.deleteById(teamId);
    }
}
