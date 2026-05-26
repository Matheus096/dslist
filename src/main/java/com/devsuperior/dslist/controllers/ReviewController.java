package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.ReviewDTO;
import com.devsuperior.dslist.services.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Pega todas as avaliações do jogo apartir do gameId
    @GetMapping(value = "/game/{gameId}")
    public ResponseEntity<List<ReviewDTO>> findReviewsByGame(@PathVariable Long gameId) {
        List<ReviewDTO> list = reviewService.findReviewsByGame(gameId);
        return ResponseEntity.ok().body(list);
    }

    // Salva a avaliação do usuário autenticado
    @PostMapping
    public ResponseEntity<ReviewDTO> insertReview(@RequestBody ReviewDTO dto) {
        dto = reviewService.insertReview(dto);
        return ResponseEntity.ok().body(dto);
    }
}
