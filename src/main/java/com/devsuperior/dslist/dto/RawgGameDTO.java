package com.devsuperior.dslist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawgGameDTO {

    private Long id;
    private String name;
    private String backgroundImage;
    private Double rating;
    private String released;

}
