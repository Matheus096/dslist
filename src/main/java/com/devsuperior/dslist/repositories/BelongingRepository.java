package com.devsuperior.dslist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dslist.entities.Belonging;
import com.devsuperior.dslist.entities.BelongingPK;
import com.devsuperior.dslist.entities.Game;

public interface BelongingRepository extends JpaRepository<Belonging, BelongingPK> {

    @Query("SELECT b.id.game FROM Belonging b WHERE b.id.list.id = :listId AND b.id.user.id = :userId ORDER BY b.position ASC")
    List<Game> findGamesByUserAndList(@Param("userId") Long userId, @Param("listId") Long listId);

}