package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/lists/{listId}/games")
    public ResponseEntity<List<GameMinDTO>> getGamesForUserList(@PathVariable Long userId, @PathVariable Long listId) {
        List<GameMinDTO> games = userService.getGamesByUserAndList(userId, listId);
        return ResponseEntity.ok(games);
    }

    @PostMapping("/games_store/{gameId}/buy")
    public ResponseEntity<Void> buyGame(@PathVariable Long gameId) {
        userService.buyGame(gameId);
        
        return ResponseEntity.ok().build();
    }

}