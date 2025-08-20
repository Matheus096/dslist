package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.services.GameListService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(value = "/gamelist")
public class GameListController {

    @Autowired
    private GameListService gameListService;

    @GetMapping
    public List<GameListDTO> findAll() {
        return gameListService.findAll();
    }

    // O método findAll() que criei no "Service" já retorna uma lista de GameListDTO
}