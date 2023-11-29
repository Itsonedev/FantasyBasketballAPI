package com.FantasyBasketball.NBAFantasy.client;

import com.FantasyBasketball.NBAFantasy.model.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class SportsApiClient {

    private final RestTemplate restTemplate;

    @Getter
//    @Value(value = "${api.sports.key}")
    private String apiKey = "82dc771758de435fb6a3cbc09344c5bc";


    private final ObjectMapper  objectMapper;

    public SportsApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public String getAllPlayersJson(){
        String url = "https://api.sportsdata.io/v3/nba/scores/json/Players?key={apiKey}";
        return restTemplate.getForObject(url, String.class, this.apiKey);
    }

    public List<Player> createPlayerList(String json) throws JsonProcessingException {
        List<Player> playerList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(json);
        int playerSize = rootNode.size();
        for (int i = 0; i < playerSize; i++){
            Player player = new Player();
            player.setFirstName(rootNode.get(i).get("FirstName").asText());
            player.setLastName(rootNode.get(i).get("LastName").asText());
            player.setPosition(rootNode.get(i).get("Position").asText());
            player.setNbaTeam(rootNode.get(i).get("Team").asText());
            player.setCollege(rootNode.get(i).get("College").asText());
            playerList.add(player);
        }
        return playerList;
    }

}
