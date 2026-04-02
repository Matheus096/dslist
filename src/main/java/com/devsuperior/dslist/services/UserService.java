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
        User user = authUserService.getCurrentUserEntity();
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game não encontrado"));

        // 1. Lista fixa padrão "Todos" (ID 3) (sempre que comprar um jogo, ele vai para essa lista de todo jeito)
        saveToSpecificList(user, game, 3L);

        // 2. Lógica para listas baseadas em Gênero:
        String genre = game.getGenre().toLowerCase();

        if (genre.contains("adventure") || genre.contains("aventura")) {
            saveToSpecificList(user, game, 1L); // ID 1 = Aventura e RPG
        } 
        
        if (genre.contains("rpg")) {
            saveToSpecificList(user, game, 1L); // Também cai na lista 1 kk (Aventura e RPG)
        }

        if (genre.contains("plataforma") || genre.contains("platform")) {
            saveToSpecificList(user, game, 2L); // ID 2 = Jogos de plataforma
        }
    }

    private void saveToSpecificList(User user, Game game, Long listId) {
        GameList list = gameListRepository.findById(listId).orElseThrow(() -> new RuntimeException("Lista " + listId + " não encontrada"));

        BelongingPK pk = new BelongingPK(game, list, user);

        // Verifica se já existe a relação para não duplicar
        if (!belongingRepository.existsById(pk)) {
            Long count = belongingRepository.countByUserAndList(user.getId(), listId);
        
            Belonging belonging = new Belonging();
            belonging.setId(pk);
            belonging.setPosition(count.intValue());

            belongingRepository.save(belonging);
        }
    }

}