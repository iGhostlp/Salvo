package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.GamePlayerDTO;
import com.codeoftheweb.salvo.dto.PlayerDTO;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.util.Util.isGuest;

@RestController
@RequestMapping("/api")
public class GameController {

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
    private PasswordEncoder passwordEncoder;

    //entrar partida
    @RequestMapping (path = "/game/{game_id}/players",method = RequestMethod.POST)
    public ResponseEntity <Map<String,Object>> join (@PathVariable long game_id , Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "User Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());
        Game gameToJoin = gameRepository.getOne(game_id);

        if(gameToJoin == null) {
            return new ResponseEntity<>(Util.makeMap("error", "No Such Game"), HttpStatus.FORBIDDEN);
        }

        long gamePlayersCount = gameToJoin.getGamePlayers().size();

        Set<Long> gamePlayerToJoin= gameToJoin.getGamePlayers() .stream()
                .map(gp ->
                        gp.getPlayer().getId()).collect(Collectors.toSet());

        if (gamePlayerToJoin.contains(player.getId())){
            return new ResponseEntity<>(Util.makeMap("error", "Already connected"), HttpStatus.ALREADY_REPORTED);
        }

        if(gamePlayersCount == 1) {
            GamePlayer gamePlayer = new GamePlayer(player, gameToJoin);
            gamePlayerRepository.save(gamePlayer);
            return  new ResponseEntity<>(Util.makeMap("gpid", gamePlayer.getId()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Util.makeMap("error" , "Game is full!"),HttpStatus.FORBIDDEN);
        }


    }
    //crear partida
    @RequestMapping (path = "/games" , method = RequestMethod.POST)
    public ResponseEntity <Object> createNewGame (Authentication authentication) {

        if (Util.isGuest(authentication))
            return new ResponseEntity<>(Util.makeMap("error","User Not Logged in" ), HttpStatus.UNAUTHORIZED);

        Game game = new Game(LocalDateTime.now());
        Player player =playerRepository.findByEmail(authentication.getName());
        GamePlayer gamePlayer = new GamePlayer(player,game);


        gameRepository.save(game);
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(Util.makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }
    //Game All
    @RequestMapping(path = "/games",method = RequestMethod.GET)
    public Map<String,Object> getGameAll(Authentication authentication) {
        Map<String,Object>  dto = new LinkedHashMap<>();

        if(isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = playerRepository.findByEmail(authentication.getName());
            PlayerDTO playerDTO   =   new PlayerDTO();
            dto.put("player", playerDTO.makePlayerDTO(player));
        }
        dto.put("games", gameRepository .findAll()
                .stream()
                .map(game -> {
                    GameDTO gameDTO =   new GameDTO();
                    return  gameDTO.makeGameDTO(game);
                })
                .collect(Collectors.toList()));
        return dto;

    }
}


