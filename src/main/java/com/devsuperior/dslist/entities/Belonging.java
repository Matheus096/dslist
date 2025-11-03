package com.devsuperior.dslist.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
@Table(name = "tb_belonging")
public class Belonging {

	@EmbeddedId
	private BelongingPK id = new BelongingPK();

	private Integer position;

	public Belonging(Game game, GameList list, User user, Integer position) {
		id.setGame(game);
		id.setList(list);
		id.setUser(user); // adiciona o usuário na relação
		this.position = position;
	}

	// Ao criar um novo objeto do tipo "Belonging", deve-se ter a referência do Game, do GameList e do User como pode se ver na imagem "modelo do projeto DSList.png" e no print_20
	// Por isso o construtor acima foi feito dessa forma, ja passando o Game, GameList e User como parâmetros, e atribuindo diretamente eles ao objeto "id" do tipo BelongingPK
}