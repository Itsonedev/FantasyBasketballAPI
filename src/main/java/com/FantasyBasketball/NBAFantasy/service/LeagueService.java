package com.FantasyBasketball.NBAFantasy.service;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public League createLeague(League league){
        return leagueRepository.save(league);
    }
    public League getLeagueById(Long leagueId){
        return leagueRepository.findById(leagueId).orElse(null);
    }

    public void updateLeague(Long leagueId, League league){
        League existingLeague = leagueRepository.findById(leagueId).get();
        existingLeague.setName(league.getName());
        existingLeague.setBuyIn(league.getBuyIn());
        leagueRepository.save(league);
    }

    public void deleteLeague(Long leagueId){
        leagueRepository.deleteById(leagueId);
    }

    public Iterable<League> getAllLeagues(){
        return leagueRepository.findAll();
    }



}
