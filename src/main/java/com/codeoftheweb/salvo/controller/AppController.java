package com.codeoftheweb.salvo.controller;
import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.GamePlayerDTO;
import com.codeoftheweb.salvo.dto.PlayerDTO;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Score;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static com.codeoftheweb.salvo.util.Util.isGuest;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    SalvoRepository salvoRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //GamePlayer perspective of the game
    @RequestMapping(path = "/game_view/{gamePlayerId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable long gamePlayerId, Authentication authentication) {
        Player player = playerRepository.findByEmail(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error","Not logged in"),HttpStatus.UNAUTHORIZED);
        }

        if (player.getId() != gamePlayer.getPlayer().getId()){
            return new ResponseEntity<>(Util.makeMap("error","This is not your board, stay back"),HttpStatus.UNAUTHORIZED);
        }
        if(Util.gameState(gamePlayer) == "WON"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(LocalDateTime.now());
                score1.setScore(1D);
                scoreRepository.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.getOpponent(gamePlayer).get().getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinishDate(LocalDateTime.now());
                score2.setScore(0D);
                scoreRepository.save(score2);
                scores.add(score1);
                scores.add(score2);

                Util.getOpponent(gamePlayer).get().getGame().setScores(scores);
            }
        }
        if(Util.gameState(gamePlayer) == "TIE") {
            if (gamePlayer.getGame().getScores().size() < 2) {
                Set<Score> scores = new HashSet<Score>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(LocalDateTime.now());
                score1.setScore(0.5D);
                scoreRepository.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.getOpponent(gamePlayer).get().getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinishDate(LocalDateTime.now());
                score2.setScore(0.5D);
                scoreRepository.save(score2);
                scores.add(score1);
                scores.add(score2);

                Util.getOpponent(gamePlayer).get().getGame().setScores(scores);
            }
        }

        GamePlayerDTO gpDTO = new GamePlayerDTO();
        return new ResponseEntity<>(gpDTO.makeGameViewDTO(gamePlayer),HttpStatus.ACCEPTED);


    }

    //leaderBoard
    @RequestMapping("/leaderBoard")
    public List<Map<String,Object>> getLeaderBoard(){
        return playerRepository .findAll()
                                .stream()
                                .map(s -> PlayerDTO.makePlayerScoreDTO(s))
                                .collect(Collectors.toList());
    }



}







