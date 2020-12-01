package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerDTO {

    public static  Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getEmail());
        dto.put("name", player.getName());
        return dto;
    }
        public static Map<String,Object> makePlayerScoreDTO(Player player){
            Map<String,Object>  dto = new LinkedHashMap();
            Map<String,Object> score = new LinkedHashMap<>();
            dto.put("id",player.getId());
            dto.put("email",player.getEmail());
            dto.put("score",score);
            score.put("total",player.getTotalScore());
            score.put("won",player.getWinScore());
            score.put("lost",player.getLoseScore());
            score.put("tied",player.getDrawScore());
            return dto;
    }
}
