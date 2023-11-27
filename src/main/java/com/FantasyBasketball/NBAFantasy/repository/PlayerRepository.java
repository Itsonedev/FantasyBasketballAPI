package com.FantasyBasketball.NBAFantasy.repository;

import com.FantasyBasketball.NBAFantasy.model.Player;
import com.FantasyBasketball.NBAFantasy.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Iterable<Player> getAllPlayersByTeamId(Long teamId);
    Iterable<Player> findPlayerByPosition(String position);
}
