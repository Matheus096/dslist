package com.devsuperior.dslist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dslist.entities.GameScreenshotsRawg;

public interface GameScreenshotRepository extends JpaRepository<GameScreenshotsRawg, Long> {
    List<GameScreenshotsRawg> findByGameId(Long gameId);

    // esse repositorio é o GameScreenshotRepositoryRawg, só não coloquei esse nome porque tinha esquecido kk
}
