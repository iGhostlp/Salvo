package com.codeoftheweb.salvo.controller;
import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.GamePlayerDTO;
import com.codeoftheweb.salvo.dto.PlayerDTO;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

   @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String,Object> getGameView(@PathVariable Long gamePlayerId){
        return GamePlayerDTO.gameView(gamePlayerRepository.findById(gamePlayerId).get());
    }


    PlayerDTO dtoPlayer = new PlayerDTO();
    @RequestMapping("/players")
    public  List<Map<String,Object>> getPlayerAll(){
        return playerRepository.findAll()
                .stream()
                .map(player -> dtoPlayer.makePlayerDTO(player))
                .collect(Collectors.toList());
    }

    GameDTO dtoGame = new GameDTO();
    @RequestMapping("/games")
    public List<Map<String,Object>> getGameAll(){
       return gameRepository.findAll()
               .stream()
               .map(game ->  dtoGame.makeGameDTO(game))
               .collect(Collectors.toList());

    }

    @RequestMapping("/leaderBoard")
    public List<Map<String,Object>> getLeaderBoard(){
        return playerRepository .findAll()
                                .stream()
                                .map(s -> PlayerDTO.makePlayerScoreDTO(s))
                                .collect(Collectors.toList());
    }
}






