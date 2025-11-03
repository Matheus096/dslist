package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.repositories.BelongingRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private BelongingRepository belongingRepository;

    public List<GameMinDTO> getGamesByUserAndList(Long userId, Long listId) {
        List<Game> listGames = belongingRepository.findGamesByUserAndList(userId, listId);
        List<GameMinDTO> dto = listGames.stream().map(game -> new GameMinDTO(game)).toList();
        return dto;
    }

}