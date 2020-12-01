package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.Score;

import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;

public class GameDTO {
    public static Map<String, Object> makeGameDTO(Game game){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreated());
        dto.put("gamePlayers", game.getGamePlayers().stream()
                .map(gp -> GamePlayerDTO.makeGamePlayerDTO(gp))
                .collect(toList()));
        dto.put("scores",game.getScores().stream().map(score -> GameScoreDTO.makePlayerScoreDTO(score)));
        return dto;
    }

}