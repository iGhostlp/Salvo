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




    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object>register(
            @RequestParam String email,
            @RequestParam String password){
        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (playerRepository.findByEmail(email) != null) {
            return  new ResponseEntity<>("Name already in use",HttpStatus.FORBIDDEN);
        }
        playerRepository.save(new Player (email,passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

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

    @RequestMapping(path = "/games",method = RequestMethod.GET)
    public Map<String,Object> getGameAll(Authentication authentication) {
           Map<String,Object>  dto = new LinkedHashMap<>();

        if(isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = playerRepository.findByEmail(authentication.getName());
            PlayerDTO   playerDTO   =   new PlayerDTO();
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

    @RequestMapping("/leaderBoard")
    public List<Map<String,Object>> getLeaderBoard(){
        return playerRepository .findAll()
                                .stream()
                                .map(s -> PlayerDTO.makePlayerScoreDTO(s))
                                .collect(Collectors.toList());
    }



}







