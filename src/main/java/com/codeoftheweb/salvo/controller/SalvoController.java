package com.codeoftheweb.salvo.controller;
import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.GamePlayerDTO;
import com.codeoftheweb.salvo.dto.PlayerDTO;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

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

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getGameView(@PathVariable Long gamePlayerId) {
        return GamePlayerDTO.makeGameViewDTO(gamePlayerRepository.findById(gamePlayerId).get());
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
    /*public Map<String,Object> np () {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("newPlayer", playerRepository.findAll()
                .stream()
                .map(newPlayer -> {
                    PlayerDTO playerDTO = new PlayerDTO();
                    return playerDTO.makePlayerDTO(newPlayer);
                })
                .collect(Collectors.toList()));
        return dto;
    }*/

    @RequestMapping("/games")
    public Map<String,Object> getGameAll(Authentication authentication) {
           Map<String,Object>  dto = new LinkedHashMap<>();

        if(isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = playerRepository.findByEmail(authentication.getName());
            PlayerDTO   playerDTO   =   new PlayerDTO();
            dto.put("player", playerDTO.makePlayerDTO(player));
        }
        dto.put("games", gameRepository.findAll()
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

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}







