package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.model.Ship;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }
    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    //Filter GamePlayer
    public static Optional <GamePlayer> getOpponent(GamePlayer gamePlayer){
        GamePlayer opponent = gamePlayer.getGame().getGamePlayers()
                .stream()
                .filter(gp -> gp.getId() !=gamePlayer.getId())
                .findFirst()
                .get();
        return Optional.of(opponent);

    }

    //Hits
    public List<String> getHits(List<String>myShots, Set<Ship>opponentShips){
        List<String> enemyShipLocations = new ArrayList<>();
        opponentShips.forEach(ship -> enemyShipLocations.addAll(ship.getLocations()));
        return myShots
                .stream()
                .filter(shot ->enemyShipLocations.stream().anyMatch(loc -> loc.equals(shot)))
                .collect(Collectors.toList());
    }

    //Sunken
    public List<Ship> getSunkenShips(Set<Salvo> mySalvoes, Set<Ship> opponentShips){
        List<String> allShots = new ArrayList<>();
        mySalvoes.forEach(salvo -> allShots.addAll(salvo.getLocations()));
        return opponentShips
                .stream()
                .filter(salvo -> allShots.stream().anyMatch(hit ->hit.equals(salvo)))
                .collect(Collectors.toList());
    }

}

