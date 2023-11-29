package com.FantasyBasketball.NBAFantasy.repository;

import com.FantasyBasketball.NBAFantasy.model.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRepository extends JpaRepository<Draft, Long> {
}
