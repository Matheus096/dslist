package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.repositories.GameListRepository;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    /* 
     Usando "lambda expression" para simplificar a conversão de GameList para GameListDTO:

     @Transactional(readOnly = true)
     public List<GameListDTO> findAll() {
        List<GameList> gameLists = gameListRepository.findAll();
        return gameLists.stream().map(list -> new GameListDTO(list)).toList();
     }
    
    */

    // Usando "method reference" para simplificar a conversão de GameList para GameListDTO:

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> gameLists = gameListRepository.findAll();
        return gameLists.stream().map(GameListDTO::new).toList();
    }
}