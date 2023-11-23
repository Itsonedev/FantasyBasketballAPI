package com.FantasyBasketball.NBAFantasy.repository;

import com.FantasyBasketball.NBAFantasy.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Iterable<Team> getAllTeamsByLeagueId(Long leagueId);

}
