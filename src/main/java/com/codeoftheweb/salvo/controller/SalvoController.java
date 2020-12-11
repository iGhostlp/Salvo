package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.SalvoRepository;
import com.codeoftheweb.salvo.repository.ShipRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class SalvoController {
    //Repos
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SalvoRepository salvoRepository;

    //Shoot Salvo
    @RequestMapping(value = "/games/players/{gamePlayerId}/salvoes", method = RequestMethod.POST)

    public ResponseEntity<Map<String, Object>> ShootSalvo(@PathVariable long gamePlayerId, @RequestBody Salvo salvo, Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not logged in"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        Long idGamePlayer= gamePlayer.getId();
        Game game = gamePlayer.getGame();
        GamePlayer opponent;

        if(idGamePlayer ==null){
            return new ResponseEntity<>(Util.makeMap("error", "GamePlayer Does Not Exists"), HttpStatus.UNAUTHORIZED);
        }

        if (player.getId() != gamePlayer.getPlayer().getId()) {
            return new ResponseEntity<>(Util.makeMap("error", "This is not your board, stay back"), HttpStatus.UNAUTHORIZED);

        }

        if (Util.getOpponent(gamePlayer).isPresent()){
            opponent= Util.getOpponent(gamePlayer).get();
        }else {
            return new ResponseEntity<>(Util.makeMap("error","there is not opponent yet"),HttpStatus.FORBIDDEN);
        }

        long myTurnSalvo= gamePlayer.getSalvo().size();
        long enemyTurnSalvo= opponent.getSalvo().size();

        if (myTurnSalvo > enemyTurnSalvo){
            return new ResponseEntity<>(Util.makeMap("error","It's your opponent's turn"),HttpStatus.FORBIDDEN);
        }

        if (gamePlayer.getSalvo().size() > 1) {
            return new ResponseEntity<>(Util.makeMap("error", "All Salvos Already Shooted"), HttpStatus.FORBIDDEN);
        }
        salvo.setTurn ((int) (myTurnSalvo+1));
        salvo.setGamePlayer(gamePlayer);
        salvoRepository.save(salvo);
        return new ResponseEntity<>(Util.makeMap("OK", "Salvo Shooted"),HttpStatus.CREATED);


    }

}