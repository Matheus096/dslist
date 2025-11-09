package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public GameDTO findById(Long gameId) {
        Game game = gameRepository.findById(gameId).get();
        GameDTO dto = new GameDTO(game);
        return dto;
    }

    /* 
    Forma resumida de retornar o GameDTO diretamente sem a necessidade de criar uma variável GameDTO como feito antes acima:

    @Transactional(readOnly = true)
    public GameDTO findById(Long gameId) {
        Game game = gameRepository.findById(gameId).get();
        return new GameDTO(game);
    }

    */

    // método com expressão lambda para converter a lista de entidades Game em uma lista de DTOs GameMinDTO:
    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Game> listGames = gameRepository.findAll();
        List<GameMinDTO> dto = listGames.stream().map(game -> new GameMinDTO(game)).toList();
        return dto;
    }


    // método com method reference para converter a lista de GameMinProjection em uma lista de DTOs GameMinDTO:
    @Transactional(readOnly = true)
    public List<GameMinDTO> findByListId(Long listId) {
        List<GameMinProjection> listGames = gameRepository.searchGamesByListId(listId);
        return listGames.stream().map(GameMinDTO::new).toList();
    }

    public GameDTO create(GameDTO gameDTO) {
        Game entity = new Game();
        copyDtoToEntity(gameDTO, entity);
        entity = gameRepository.save(entity);
        return new GameDTO(entity);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    public GameDTO update(Long id, GameDTO updatedGame) {
        Game entity = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        copyDtoToEntity(updatedGame, entity);
        entity = gameRepository.save(entity);
        return new GameDTO(entity);
    }

    // No Spring o repository faz operações apenas com a entidade em si, não com DTOs, então como o front envia um DTO,
    // precisamos converter esse DTO em uma entidade para que o repository consiga fazer as operações de CRUD etc
    // Esse método abaixo faz essa conversão de DTO para entidade copiando todos os atributos do DTO para a entidade tirando o id:
    private void copyDtoToEntity(GameDTO dto, Game entity) {
        BeanUtils.copyProperties(dto, entity, "id"); // ignora o id ao copiar as propriedades, se o id for copiado, pode gerar conflito ao tentar criar uma nova entidade com um id que já existe
    }
}