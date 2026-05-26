package com.devsuperior.dslist.dto;

import java.time.Instant;

import com.devsuperior.dslist.entities.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private Long gameId;
    private String text;
    private Integer rating;
    private Instant createdAt;
    private Long userId;
    private String userName;

    public ReviewDTO(Review entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.rating = entity.getRating();
        this.createdAt = entity.getCreatedAt();
        
        if (entity.getGame() != null) {
            this.gameId = entity.getGame().getId();
        }
        if (entity.getUser() != null) {
            this.userId = entity.getUser().getId();
            this.userName = entity.getUser().getUsername();
        }
    }

}
