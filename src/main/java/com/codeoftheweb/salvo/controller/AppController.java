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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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







