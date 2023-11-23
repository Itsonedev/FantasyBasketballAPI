package com.FantasyBasketball.NBAFantasy.repository;

import com.FantasyBasketball.NBAFantasy.model.League;
import com.FantasyBasketball.NBAFantasy.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
