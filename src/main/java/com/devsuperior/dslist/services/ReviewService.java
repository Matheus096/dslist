package com.devsuperior.dslist.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.ReviewDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.entities.Review;
import com.devsuperior.dslist.entities.User;
import com.devsuperior.dslist.repositories.GameRepository;
import com.devsuperior.dslist.repositories.ReviewRepository;
import com.devsuperior.dslist.repositories.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReviewDTO> findReviewsByGame(Long gameId) {
        List<Review> list = reviewRepository.findByGameIdOrderByCreatedAtDesc(gameId);
        return list.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO insertReview(ReviewDTO dto) {

        // 1. Mapeia a entidade de Review com os dados enviados pelo front
        Review review = new Review();
        review.setText(dto.getText());
        review.setRating(dto.getRating());
        review.setCreatedAt(Instant.now());
        
        // 2. Vincula o Game real do banco, buscando pelo ID vindo do front
        Game game = gameRepository.findById(dto.getGameId()).orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + dto.getGameId()));
        review.setGame(game);
        
        // 3. Vincula o User atual logado, o recuperando direto do Contexto de Segurança (JWT) usando o email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        review.setUser(user);
        
        // 4. Salva e retorna o DTO atualizado
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }

}
