package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository,ScoreRepository scoreRepository) {
		return (args) -> {
			//Players
			Player p1 = new Player("Jack Bauer ", "j.bauer@ctu.gov ");
			Player p2 = new Player("Chloe O'Brian ", "c.obrian@ctu.gov");
			Player p3 = new Player("Kim bauer ", "kim_bauer@gmail.com");
			Player p4 = new Player("David Palmer ", "t.almeida@ctu.gov");
			Player p5 = new Player("Franco", "franco.rap@hotmail.com");
			Player p6 = new Player("Lautaro", "launicolopez@hotmail.com");
			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);
			playerRepository.save(p5);
			playerRepository.save(p6);

			//Games
			Game g1 = new Game(LocalDateTime.now().plusHours(0));
			Game g2 = new Game(LocalDateTime.now().plusHours(1));
			Game g3 = new Game(LocalDateTime.now().plusHours(2));
			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);


			//GamePlayer
			GamePlayer gamePlayer1 = new GamePlayer(p1, g1);
			GamePlayer gamePlayer2 = new GamePlayer(p2, g1);
			GamePlayer gamePlayer3 = new GamePlayer(p3, g2);
			GamePlayer gamePlayer4 = new GamePlayer(p4, g2);
			GamePlayer gamePlayer5 = new GamePlayer(p5, g3);
			GamePlayer gamePlayer6 = new GamePlayer(p6, g3);


			//Ships Player 1
			Ship ship1 = new Ship("Carrier", List.of("C1", "C2", "C3", "C4", "C5"));
			Ship ship2 = new Ship("Battleship", List.of("F3", "F4", "F5", "F6"));
			Ship ship3 = new Ship("Submarine", List.of("H1", "H2", "H3"));
			Ship ship4 = new Ship(" Destroyer", List.of("D2", "D3", "D4"));
			Ship ship5 = new Ship("Patrol Boat", List.of("A1", "A2"));
			//Ship Player 2
			Ship ship6 = new Ship("Carrier", List.of("C9", "C8", "C7", "C6", "C5"));
			Ship ship7 = new Ship("Battleship", List.of("F3", "F4", "F5", "F6"));
			Ship ship8 = new Ship("Submarine", List.of("H1", "H2", "H3"));
			Ship ship9 = new Ship(" Destroyer", List.of("D2", "D3", "D4"));
			Ship ship10 = new Ship("Patrol Boat", List.of("A1", "A2"));

			//Repository Player 1
			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);

			//Repository Player 2
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);

			//Salvoes
			Salvo salvo1 = new Salvo(1 ,List.of("D7","J1","B3","G6","H4"));
			/*Salvo salvo2 = new Salvo(2 ,List.of("J7","J6","B3","G6","H4"));
			Salvo salvo3 = new Salvo(3 ,List.of("J8","B9","B3","G6","H4"));
			Salvo salvo4 = new Salvo(4 ,List.of("F9","","B3","G6","H4"));
			Salvo salvo5 = new Salvo(5 ,List.of("A3","J1","B3","G6","H4"));*/
			salvoRepository.save(salvo1);
			//Salvoes p2
			Salvo salvo6 = new Salvo(1 ,List.of("D3","J7","B8","G9","H1"));
			/*Salvo salvo2 = new Salvo(2 ,List.of("J7","J6","B3","G6","H4"));
			Salvo salvo3 = new Salvo(3 ,List.of("J8","B9","B3","G6","H4"));
			Salvo salvo4 = new Salvo(4 ,List.of("F9","","B3","G6","H4"));
			Salvo salvo5 = new Salvo(5 ,List.of("A3","J1","B3","G6","H4"));*/
			salvoRepository.save(salvo6);

			//Score p1
			Score scorep1= new Score(0.5,(LocalDateTime.now()),g1,p1);
			scoreRepository.save(scorep1);
			//Score p2
			Score score2 = new Score(0.5,(LocalDateTime.now()),g1,p2);
			scoreRepository.save(score2);






			gamePlayer1.addShip(ship1);
			gamePlayer1.addShip(ship2);
			gamePlayer1.addShip(ship3);
			gamePlayer1.addShip(ship4);
			gamePlayer1.addShip(ship5);
			gamePlayer1.addSalvo(salvo1);
			gamePlayerRepository.save(gamePlayer1);

			gamePlayer2.addShip(ship6);
			gamePlayer2.addShip(ship7);
			gamePlayer2.addShip(ship8);
			gamePlayer2.addShip(ship9);
			gamePlayer2.addShip(ship10);
			gamePlayer2.addSalvo(salvo6);
			gamePlayerRepository.save(gamePlayer2);


			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer6);

		};
	}
}

