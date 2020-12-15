package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.util.Util;

import javax.persistence.OrderBy;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class GamePlayerDTO {

    public static Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", PlayerDTO.makePlayerDTO(gamePlayer.getPlayer()));

        return dto;
    }

    public static Map<String, Object> makeGameViewDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = GameDTO.makeGameDTO(gamePlayer.getGame());
        Map<String, Object> hits = new LinkedHashMap<>();
        HitsDTO hitsDTO = new HitsDTO();


            dto.put("ships", gamePlayer.getShips().stream()
                    .map(s -> ShipDTO.makeShipDTO(s))
                    .collect(toList()));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                    .stream()
                    .flatMap(gp -> gp.getSalvo().stream()
                            .map(salvo -> {
                                SalvoDTO salvoDTO = new SalvoDTO();
                                return salvoDTO.makeSalvoDTO(salvo);
                            }))
                    .collect(Collectors.toList()));

        if (gamePlayer.getGame().getGamePlayers().size()==2) {
            hits.put("self", hitsDTO.makeHitsDTO(gamePlayer));
            hits.put("opponent", hitsDTO.makeHitsDTO(gamePlayer.getOpponent()));
        }else{

            hits.put("self",new ArrayList<>());
            hits.put("opponent",new ArrayList<>());
        }
            dto.put("hits", hits);
            dto.put("gameState", Util.gameState(gamePlayer));
            return dto;
        }

    }

