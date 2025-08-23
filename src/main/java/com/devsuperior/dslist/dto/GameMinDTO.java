package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;

public class GameMinDTO {
    
    private Long id;
    private String title;
    private Integer year;
    private String imgUrl;
    private String shortDescription;

    public GameMinDTO() {
    }

    // Construtor que recebe uma entidade do tipo Game e converte para GameMinDTO:
    public GameMinDTO(Game entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.year = entity.getYear();       
        this.imgUrl = entity.getImgUrl();
        this.shortDescription = entity.getShortDescription();
    }

    // Construtor que recebe uma projection do tipo GameMinProjection e converte para GameMinDTO:
    public GameMinDTO(GameMinProjection projection) {
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.year = projection.getYear();       
        this.imgUrl = projection.getImgUrl();
        this.shortDescription = projection.getShortDescription();
    }

    // Se voce apagar algum desses construtores, vai dar erro lá no GameService na hora da conversão de (entidade Game ou projection GameMinProjection) para GameMinDto, reclamando que tal construtor não existe mais

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}