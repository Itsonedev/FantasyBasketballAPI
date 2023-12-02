package com.FantasyBasketball.NBAFantasy.service;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.model.Team;
import com.FantasyBasketball.NBAFantasy.repository.PlayerRepository;
import com.FantasyBasketball.NBAFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DraftService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    public Player getNextPlayer(List<Player> availablePlayers, int currentIndex){
        if (currentIndex < availablePlayers.size()){
            return availablePlayers.get(currentIndex);
        }
        return null;
    }

    @Transactional
    public void conductDraft(League league){
        List<Team> teams = league.getTeams();
        List<Player> players = playerRepository.findAll();
        int currentPlayer = 0;

        for (int round = 1; round <= 10; round++){
            Collections.shuffle(players);
            for (Team team : teams){
                Player selectedPlayer = getNextPlayer(players, currentPlayer);

                if (selectedPlayer != null){
                    playerService.draftPlayerToTeam(selectedPlayer.getId(), team.getId());
                    currentPlayer++;
                }
            }
        }
    }
}
