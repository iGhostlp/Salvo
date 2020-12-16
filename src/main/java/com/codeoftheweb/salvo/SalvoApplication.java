package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

//	@Bean
//	public CommandLineRunner initData(PlayerRepository playerRepository,
//									  GameRepository gameRepository,
//									  GamePlayerRepository gamePlayerRepository,
//									  ShipRepository shipRepository,
//									  SalvoRepository salvoRepository,
//									  ScoreRepository scoreRepository) {
//		return (args) -> {
//			//Players
//			Player p1 = new Player("Jack Bauer", "j.bauer@ctu.gov", passwordEncoder().encode("1"));
//			Player p2 = new Player("Chloe O'Brian", "c.obrian@ctu.gov",passwordEncoder().encode("2"));
//			Player p3 = new Player("Kim bauer", "kim_bauer@gmail.com",passwordEncoder().encode("3"));
//			Player p4 = new Player("David Palmer ", "t.almeida@ctu.gov",passwordEncoder().encode("4"));
//			Player p5 = new Player("Franco", "franco.rap@hotmail.com",passwordEncoder().encode("5"));
//			Player p6 = new Player("Lautaro", "launicolopez@hotmail.com",passwordEncoder().encode("6"));
//			playerRepository.save(p1);
//			playerRepository.save(p2);
//			playerRepository.save(p3);
//			playerRepository.save(p4);
//			playerRepository.save(p5);
//			playerRepository.save(p6);
//
//			//Games
//			Game g1 = new Game(LocalDateTime.now().plusHours(0));
//			Game g2 = new Game(LocalDateTime.now().plusHours(1));
//			Game g3 = new Game(LocalDateTime.now().plusHours(2));
//			gameRepository.save(g1);
//			gameRepository.save(g2);
//			gameRepository.save(g3);
//
//
//			//GamePlayer
//			GamePlayer gamePlayer1 = new GamePlayer(p1, g1);
//			GamePlayer gamePlayer2 = new GamePlayer(p2, g1);
//			GamePlayer gamePlayer3 = new GamePlayer(p3, g2);
//			GamePlayer gamePlayer4 = new GamePlayer(p4, g2);
//			GamePlayer gamePlayer5 = new GamePlayer(p5, g3);
//			GamePlayer gamePlayer6 = new GamePlayer(p6, g3);
//
//
//			//Ships Player 1
//			Ship ship1 = new Ship("carrier", List.of("C1", "C2", "C3", "C4", "C5"));
//			Ship ship2 = new Ship("battleship", List.of("F3", "F4", "F5", "F6"));
//			Ship ship3 = new Ship("submarine", List.of("H1", "H2", "H3"));
//			Ship ship4 = new Ship("destroyer", List.of("D2", "D3", "D4"));
//			Ship ship5 = new Ship("patrolboat", List.of("A1", "A2"));
//			//Ship Player 2
//			Ship ship6 = new Ship("carrier", List.of("C9", "C8", "C7", "C6", "C5"));
//			Ship ship7 = new Ship("battleship", List.of("F3", "F4", "F5", "F6"));
//			Ship ship8 = new Ship("submarine", List.of("H1", "H2", "H3"));
//			Ship ship9 = new Ship("destroyer", List.of("D2", "D3", "D4"));
//			Ship ship10 = new Ship("patrolboat", List.of("A1", "A2"));
//
//			//Repository Player 1
//			shipRepository.save(ship1);
//			shipRepository.save(ship2);
//			shipRepository.save(ship3);
//			shipRepository.save(ship4);
//			shipRepository.save(ship5);
//
//			//Repository Player 2
//			shipRepository.save(ship6);
//			shipRepository.save(ship7);
//			shipRepository.save(ship8);
//			shipRepository.save(ship9);
//			shipRepository.save(ship10);
//
//			//Salvoes
//			Salvo salvo1 = new Salvo(1 ,List.of("C9", "C8", "C7", "C6", "C5"));
//			Salvo salvo2 = new Salvo(2 ,List.of("F3", "F4", "F5", "F6"));
//			Salvo salvo3 = new Salvo(3 ,List.of("H1", "H2", "H3"));
//			Salvo salvo4 = new Salvo(4 ,List.of("D2", "D3", "D4"));
////			Salvo salvo5 = new Salvo(5 ,List.of("A3","J1","B3","G6","H4"));*/
//			salvoRepository.save(salvo1);
//			salvoRepository.save(salvo2);
//			salvoRepository.save(salvo3);
//			salvoRepository.save(salvo4);
//			//Salvoes p2
//			Salvo salvo6 = new Salvo(1 ,List.of("C1", "C2", "C3", "C4", "C5"));
//			Salvo salvo7 = new Salvo(2 ,List.of("H1", "H2", "H3"));
//			Salvo salvo8 = new Salvo(3 ,List.of("F3", "F4", "F5", "F6"));
//			Salvo salvo9 = new Salvo(4 ,List.of("D2", "D3", "D4"));
////			Salvo salvo10 = new Salvo(5 ,List.of("A3","J1","B3","G6","H4"));*/
//			salvoRepository.save(salvo6);
//			salvoRepository.save(salvo7);
//			salvoRepository.save(salvo8);
//			salvoRepository.save(salvo9);
//
//			//Score p1
////			Score scorep1= new Score(0.5,(LocalDateTime.now()),g1,p1);
////			scoreRepository.save(scorep1);
////			//Score p2
////			Score score2 = new Score(0.5,(LocalDateTime.now()),g1,p2);
////			scoreRepository.save(score2);
//
////			Score score3 = new Score(1,(LocalDateTime.now()),g2,p3);
////			scoreRepository.save(score3);
////			Score score4 = new Score(0,(LocalDateTime.now()),g2,p4);
////			scoreRepository.save(score4);
//
//			gamePlayer1.addShip(ship1);
//			gamePlayer1.addShip(ship2);
//			gamePlayer1.addShip(ship3);
//			gamePlayer1.addShip(ship4);
//			gamePlayer1.addShip(ship5);
//			gamePlayer1.addSalvo(salvo1);
//			gamePlayer1.addSalvo(salvo2);
//			gamePlayer1.addSalvo(salvo3);
//			gamePlayer1.addSalvo(salvo4);
//			gamePlayerRepository.save(gamePlayer1);
//
//			gamePlayer2.addShip(ship6);
//			gamePlayer2.addShip(ship7);
//			gamePlayer2.addShip(ship8);
//			gamePlayer2.addShip(ship9);
//			gamePlayer2.addShip(ship10);
//			gamePlayer2.addSalvo(salvo6);
//			gamePlayer2.addSalvo(salvo7);
//			gamePlayer2.addSalvo(salvo8);
//			gamePlayer2.addSalvo(salvo9);
//			gamePlayerRepository.save(gamePlayer2);
//
//
//			gamePlayerRepository.save(gamePlayer3);
//			gamePlayerRepository.save(gamePlayer4);
//			gamePlayerRepository.save(gamePlayer5);
//			gamePlayerRepository.save(gamePlayer6);
//			gamePlayerRepository.save(gamePlayer6);
//
//		};
//	}
}
//WEB SECURITY
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByEmail(inputName);
			if (player != null) {
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/api/players").permitAll()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/api/game_view/*").hasAuthority("USER")
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/games").permitAll();


		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();
		http.headers().frameOptions().disable();


		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}