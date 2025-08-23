package com.devsuperior.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

	@Query(nativeQuery = true, value = """
		SELECT tb_game.id, tb_game.title, tb_game.game_year AS "year", tb_game.img_url AS imgUrl,
		tb_game.short_description AS shortDescription, tb_belonging.position
		FROM tb_game
		INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
		WHERE tb_belonging.list_id = :listId
		ORDER BY tb_belonging.position
		""")
	List<GameMinProjection> searchByList(Long listId);

	// "@Query" diz ao Spring Data JPA que esse método (searchByList) não vai usar o nome do método para criar a query automaticamente
    // Em vez disso, está dizendo: “Use essa query SQL que estou escrevendo aqui”

	// O parâmetro ":listId" na query é um placeholder que será substituído pelo valor passado no parâmetro "listId" do método searchByList


	// Teste Inner Join:
	// adicionei um jogo a mais (Zelda), apenas na tabela de jogos, mas sem adicionar na tabela de pertencimento a uma lista (belonging), para testes, adicionei lá no import.sql
    // fiz esse teste em especifico para verificar como funciona o "INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id" na query acima, que traz apenas os jogos que estão em alguma lista específica
	// ou seja, o jogo 'The Legend of Zelda' não deve aparecer em nenhuma lista após a consulta, pois ele não foi adicionado a nenhuma lista na tabela de pertencimento (belonging) --
}