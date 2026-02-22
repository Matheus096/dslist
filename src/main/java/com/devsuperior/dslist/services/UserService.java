package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Belonging;
import com.devsuperior.dslist.entities.BelongingPK;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.entities.User;
import com.devsuperior.dslist.repositories.BelongingRepository;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private BelongingRepository belongingRepository;
    private AuthUserService authUserService;
    private GameRepository gameRepository;
    private GameListRepository gameListRepository;

    public List<GameMinDTO> getGamesByUserAndList(Long userId, Long listId) {
        List<Game> listGames = belongingRepository.findGamesByUserAndList(userId, listId);
        List<GameMinDTO> dto = listGames.stream().map(game -> new GameMinDTO(game)).toList();
        return dto;
    }

    @Transactional
    public void buyGame(Long gameId) {
        Long listId = 3L; // lista fixa "Todos"

        User user = authUserService.getCurrentUserEntity();
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game não encontrado"));
        GameList list = gameListRepository.findById(listId).orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        // Verifica se o user já possui o jogo na lista "Todos"
        BelongingPK pk = new BelongingPK(game, list, user);

        if (belongingRepository.existsById(pk)) {
            throw new RuntimeException("Usuário já possui esse jogo");
        }

        // Pega próxima posição
        Long count = belongingRepository.countByUserAndList(user.getId(), listId);
        Integer position = count.intValue();

        Belonging belonging = new Belonging();
        belonging.setId(pk);
        belonging.setPosition(position);

        belongingRepository.save(belonging);
    }

}