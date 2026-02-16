package com.devsuperior.dslist.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsuperior.dslist.dto.GameScreenshotsRawgDTO;
import com.devsuperior.dslist.dto.RawgGameDTO;
import com.devsuperior.dslist.entities.GameScreenshotsRawg;
import com.devsuperior.dslist.repositories.GameScreenshotRepository;

@Service
public class RawgService {

    private GameScreenshotRepository screenshotRepository;

    public RawgService(GameScreenshotRepository screenshotRepository) {
        this.screenshotRepository = screenshotRepository;
    }

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RawgGameDTO> searchGames(String search) {

        String url = apiUrl + "/games?key=" + apiKey + "&search=" + search + "&page_size=10";
        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<RawgGameDTO> games = new ArrayList<>();

        for (Map<String, Object> game : results) {
            Long id = ((Number) game.get("id")).longValue();
            String name = (String) game.get("name");
            String backgroundImage = (String) game.get("background_image");
            Double rating = game.get("rating") != null ? ((Number) game.get("rating")).doubleValue() : 0.0;
            String released = (String) game.get("released");

            games.add(new RawgGameDTO(id, name, backgroundImage, rating, released));
        }

        // Eu poderia criar um construtor no RawgGameDTO que recebe o Map e fazer a conversão lá igual fiz com "GameDTO", invés de fazer esse "for" aqui, mas decidi fazer na mão aqui pra deixar registrado uma forma alternativa de realizar o mesmo processo  

        return games;
    }

    public List<GameScreenshotsRawgDTO> getScreenshots(Long gameId) {

        String url = apiUrl + "/games/" + gameId + "/screenshots?key=" + apiKey;
        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<GameScreenshotsRawgDTO> screenshots = new ArrayList<>();

        for (Map<String, Object> shot : results) {
            Long id = ((Number) shot.get("id")).longValue();
            String image = (String) shot.get("image");

            screenshots.add(new GameScreenshotsRawgDTO(id, image));
        }

        // O mesmo aqui, igual o que comentei no método "searchGames", poderia criar um construtor no GameScreenshotsRawgDTO que recebe o Map e fazer a conversão lá

        return screenshots;
    }

    public List<GameScreenshotsRawgDTO> getScreenshotsMeuBd(Long gameId) {

        List<GameScreenshotsRawg> screenshots = screenshotRepository.findByGameId(gameId);

        return screenshots.stream()
                .map(screenshot -> new GameScreenshotsRawgDTO(screenshot.getId(), screenshot.getImageUrl()))
                .toList();
    }
}
