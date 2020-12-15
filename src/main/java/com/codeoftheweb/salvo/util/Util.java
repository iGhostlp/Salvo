package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.dto.HitsDTO;
import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.*;


public class Util {

    //make map
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    //Guest
    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    //Filter GamePlayer
    public static Optional<GamePlayer> getOpponent(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getGamePlayers()
                .stream()
                .filter(gp -> gp.getId() != gamePlayer.getId())
                .findFirst()
                .get();
        return Optional.of(opponent);

    }

    //GetTypes
    public static List<String> getLocatiosByType(String type, GamePlayer self) {
        return self.getShips().size() == 0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
    }

    //Game States
    public static String gameState(GamePlayer gamePlayer) {
        Map<String, Object> hits = new LinkedHashMap<>();

        if (gamePlayer.getShips().isEmpty()) {
            return "PLACESHIPS";
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 1 || Util.getOpponent(gamePlayer).get().getShips().size() == 0) {
            return "WAITINGFOROPP";
        }

        long myTurnSalvo = gamePlayer.getSalvo().size();
        long enemyTurnSalvo = gamePlayer.getOpponent().getSalvo().size();
        if (myTurnSalvo > enemyTurnSalvo) {
            return "WAIT";
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 2 && gamePlayer.getSalvo().size() > Util.getOpponent(gamePlayer).get().getSalvo().size()) {


        HitsDTO hitsDTO = new HitsDTO();
        int mySelfImpact = hitsDTO.getSunkenDTO(gamePlayer);
        int opponentImpact = hitsDTO.getSunkenDTO(gamePlayer.getOpponent());


        if (mySelfImpact == 17 && opponentImpact == 17) {

            return "TIE";
        } else if (mySelfImpact == 17) {
            return "LOSE";
        } else if (opponentImpact == 17) {
            return "WON";
        }
    }
        return "PLAY";
    }
}

















