package com.FantasyBasketball.NBAFantasy.factory;

import com.FantasyBasketball.NBAFantasy.model.Draft;
import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
@Component
public class DraftFactory {

    @Autowired
    private TeamRepository teamRepository;

    public Draft generateDraft(List<Player> players){
        int playerSize = players.size();
        Random random = new Random();
        Set<Integer> chosenIds = new HashSet<>();
        long teamSize = teamRepository.count();
        List<Player> draftList = new ArrayList<>();

        for (int i = 0; i < teamSize; i++){
            for (int j = 0; j < 13; j++){
                int randomNum = random.nextInt(playerSize);
//                while (!chosenIds.contains(randomNum)){
//                    randomNum = random.nextInt(playerSize);
//                }
                draftList.add(players.get(randomNum));
                chosenIds.add(randomNum);
            }
        } Draft draft = new Draft();
        draft.setPlayers(draftList);
        return draft;
    }
}
