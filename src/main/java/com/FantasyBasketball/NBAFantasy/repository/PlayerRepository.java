package com.FantasyBasketball.NBAFantasy.repository;

import com.FantasyBasketball.NBAFantasy.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
