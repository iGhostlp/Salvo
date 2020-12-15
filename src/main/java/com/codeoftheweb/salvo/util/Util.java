package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.dto.HitsDTO;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.model.Ship;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

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

    //Sunken
    public static List <Ship> getSunkenShips(Set<Salvo> mySalvoes , Set<Ship> opponentShips){
        List<String> allShots = new ArrayList<>();
        mySalvoes.forEach(salvo -> allShots.addAll(salvo.getLocations()));
        return opponentShips
                .stream()
                .filter(salvo -> allShots.stream().anyMatch(hit -> hit.equals(salvo)))
                .collect(Collectors.toList());

    }

    //Game States
    public static String gameState(GamePlayer gamePlayer) {
        Map<String, Object> hits = new LinkedHashMap<>();
        HitsDTO hitsDTO = new HitsDTO();
        int totalDamagecarrier = 5;
        int totalDamagebattleship = 4;
        int totalDamagesubmarine = 3;
        int totalDamagedestroyer= 3;
        int totalDamagepatrolboat = 2;


        if (gamePlayer.getShips().isEmpty()) {
            return "PLACESHIPS";
        }

        if (gamePlayer.getGame().getGamePlayers().size() == 1) {
            return "WAITINGFOROPP";
        }
        if (gamePlayer.getGame().getGamePlayers().size()==2){
            return "PLAY";
        }


        if (Util.getOpponent(gamePlayer).get().getShips().size()>=1){
            return "PLAY";
        }

         if (Util.getOpponent(gamePlayer).get().getShips().isEmpty()){

            return "WON";
        }


        if (gamePlayer.getShips().size() == 0) {
            return "LOST";
        } else
            return "PLAY";


    }


}








