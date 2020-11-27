package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;

public class GamePlayerDTO {
    public static Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", PlayerDTO.makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }
    public static Map<String, Object> gameView(GamePlayer gamePlayer) {
        Map<String, Object> dto = GameDTO.makeGameDTO(gamePlayer.getGame());
        dto.put("ships",gamePlayer.getShip().stream()
                                            .map(s -> ShipDTO.makeShipDTO(s))
                                            .collect(toList()));
        return  dto;
    }
}
