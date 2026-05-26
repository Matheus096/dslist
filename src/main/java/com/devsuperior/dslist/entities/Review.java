package com.devsuperior.dslist.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_review")
public class Review {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;
    
    private Integer rating;
    private Instant createdAt;

    // Relacionamento com o Jogo local
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // Relacionamento com o Usuário logado
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
