package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.util.Util;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class HitsDTO {

    private Map<String, Object> dto;

    public HitsDTO() {
        this.dto = new LinkedHashMap<>();
    }

    public List<Map<String, Object>> makeHitsDTO(GamePlayer self) {
        List<Map<String, Object>> hits = new ArrayList<>();


        //Type Of Ships
        List<String> carrierLocations = Util.getLocatiosByType("carrier", self);
        List<String> battleshipLocations = Util.getLocatiosByType("battleship", self);
        List<String> submarineLocations = Util.getLocatiosByType("submarine", self);
        List<String> destroyerLocations = Util.getLocatiosByType("destroyer", self);
        List<String> patrolboatLocations = Util.getLocatiosByType("patrolboat", self);


        //Damage total
        long carrierDamage = 0;
        long battleshipDamage = 0;
        long submarineDamage = 0;
        long destroyerDamage = 0;
        long patrolboatDamage = 0;

        for (Salvo salvoShot : Util.getOpponent(self).get().getSalvo()) {
            Map<String, Object> hitsMapPerTurn = new LinkedHashMap<>();
            List<String> hitCellsList = new ArrayList<>();
            Map<String, Object> damagePerTurn = new LinkedHashMap<>();
            //Missed shots
            long missedShot = salvoShot.getLocations().size();
            //Damage Per Turn
            long carrierHitsInTurn = 0;
            long battleshipHitsInTurn = 0;
            long submarineHitsInTurn = 0;
            long destroyerHitsInTurn = 0;
            long patrolboatHitsInTurn = 0;

            for (String location : salvoShot.getLocations()) {

                if (carrierLocations.contains(location)) {
                    hitCellsList.add(location);
                    carrierDamage++;
                    carrierHitsInTurn++;
                    missedShot--;
                }

                if (battleshipLocations.contains(location)) {
                    hitCellsList.add(location);
                    battleshipDamage++;
                    battleshipHitsInTurn++;
                    missedShot--;
                }

                if (submarineLocations.contains(location)) {
                    hitCellsList.add(location);
                    submarineDamage++;
                    submarineHitsInTurn++;
                    missedShot--;
                }

                if (destroyerLocations.contains(location)) {
                    hitCellsList.add(location);
                    destroyerDamage++;
                    destroyerHitsInTurn++;
                    missedShot--;
                }

                if (patrolboatLocations.contains(location)) {
                    hitCellsList.add(location);
                    patrolboatDamage++;
                    patrolboatHitsInTurn++;
                    missedShot--;
                }
            }
            //Damages
            damagePerTurn.put("carrierHits", carrierHitsInTurn);
            damagePerTurn.put("battleshipHits", battleshipHitsInTurn);
            damagePerTurn.put("submarineHits", submarineHitsInTurn);
            damagePerTurn.put("destroyerHits", destroyerHitsInTurn);
            damagePerTurn.put("patrolboatHits", patrolboatHitsInTurn);
            damagePerTurn.put("carrier", carrierDamage);
            damagePerTurn.put("battleship", battleshipDamage);
            damagePerTurn.put("submarine", submarineDamage);
            damagePerTurn.put("destroyer", destroyerDamage);
            damagePerTurn.put("patrolboat", patrolboatDamage);

            hitsMapPerTurn.put("turn", salvoShot.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damagePerTurn);
            hitsMapPerTurn.put("missed", missedShot);
            hits.add(hitsMapPerTurn);
        }


        return hits;
    }

    public int getSunkenDTO(GamePlayer gamePlayer) {
        List<String> carrierLocations = new ArrayList<String>();
        List<String> battleshipLocations = new ArrayList<>();
        List<String> submarineLocations = new ArrayList<>();
        List<String> destroyerLocations = new ArrayList<>();
        List<String> patrolBoatLocations = new ArrayList<>();

        carrierLocations = Util.getLocatiosByType("carrier", gamePlayer);
        battleshipLocations = Util.getLocatiosByType("battleship", gamePlayer);
        submarineLocations = Util.getLocatiosByType("submarine", gamePlayer);
        destroyerLocations = Util.getLocatiosByType("destroyer", gamePlayer);
        patrolBoatLocations = Util.getLocatiosByType("patrolboat", gamePlayer);

        int countImpact = 0;

        for (Salvo salvoShot : Util.getOpponent(gamePlayer).get().getSalvo()) {

            for (String location : salvoShot.getLocations()) {
                if (carrierLocations.contains(location)) {
                    countImpact++;
                }
                if (battleshipLocations.contains(location)) {
                    countImpact++;
                }
                if (submarineLocations.contains(location)) {
                    countImpact++;
                }
                if (destroyerLocations.contains(location)) {
                    countImpact++;
                }
                if (patrolBoatLocations.contains(location)) {
                    countImpact++;
                }
            }

        }
        return countImpact++;


    }
}

















