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

}
