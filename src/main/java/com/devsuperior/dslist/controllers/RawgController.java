package com.devsuperior.dslist.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dslist.dto.GameScreenshotsRawgDTO;
import com.devsuperior.dslist.dto.RawgGameDTO;
import com.devsuperior.dslist.services.RawgService;

@RestController
@RequestMapping("/api/rawg")
public class RawgController {

    private final RawgService rawgService;

    public RawgController(RawgService rawgService) {
        this.rawgService = rawgService;
    }

    @GetMapping("/games")
    public List<RawgGameDTO> searchGames(@RequestParam String search) {
        return rawgService.searchGames(search);
    }

    @GetMapping("/games/{id}/screenshots/rawg_bd")
    public List<GameScreenshotsRawgDTO> getScreenshots(@PathVariable Long id) {
        return rawgService.getScreenshots(id);
    }

    @GetMapping("/games/{id}/screenshots/meu_bd")
    public List<GameScreenshotsRawgDTO> getScreenshotsMeuBd(@PathVariable Long id) {
        return rawgService.getScreenshotsMeuBd(id);
    }
}
