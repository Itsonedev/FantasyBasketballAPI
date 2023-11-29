package com.FantasyBasketball.NBAFantasy;

import com.FantasyBasketball.NBAFantasy.client.SportsApiClient;
import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class NbaFantasyApplication {

	@Autowired
	private PlayerRepository playerRepository;


	public void initializePlayerRepository() throws Exception{
		SportsApiClient sportsApiClient = new SportsApiClient(new RestTemplate());
		String playerJson = sportsApiClient.getAllPlayersJson();
		List<Player> playerList = sportsApiClient.createPlayerList(playerJson);
		playerRepository.saveAll(playerList);
	}

	@Bean
	public CommandLineRunner run() throws Exception{
		return args -> initializePlayerRepository();
	}


	public static void main(String[] args) {
		SpringApplication.run(NbaFantasyApplication.class, args);
	}



}
