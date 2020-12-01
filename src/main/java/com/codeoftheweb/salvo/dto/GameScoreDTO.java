package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Score;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameScoreDTO {
    public static Map<String, Object> makePlayerScoreDTO(Score score) {
        Map<String, Object> dto = new LinkedHashMap();
        dto.put("player", score.getPlayer().getId());
        dto.put("finishDate", score.getFinishDate());
        dto.put("score", score.getScore());

        return dto;
    }
}
