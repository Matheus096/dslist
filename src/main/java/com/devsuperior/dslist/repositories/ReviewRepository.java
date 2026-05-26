package com.devsuperior.dslist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dslist.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Busca automática: SELECT * FROM tb_review WHERE game_id = ? ORDER BY created_at DESC
    List<Review> findByGameIdOrderByCreatedAtDesc(Long gameId);

}
