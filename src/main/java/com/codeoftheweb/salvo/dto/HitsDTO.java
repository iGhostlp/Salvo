package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.util.Util;
import java.util.*;

public class HitsDTO {

    private Map<String,Object> dto;
    public HitsDTO(){
        this.dto = new LinkedHashMap<>();
    }

    public List<Map<String, Object>> makeHitsDTO(GamePlayer self){
        List<Map<String, Object>>hits = new ArrayList<>();
        Map<String, Object>hitsMapPerTurn = new LinkedHashMap<>();
        Map<String,Object>damagePerTurn= new LinkedHashMap<>();
        List<String>hitCellsList = new ArrayList<>();

        //Type Of Ships
        List<String>carrierLocations =Util.getLocatiosByType("carrier", self);
        List<String>battleshipLocations =Util.getLocatiosByType("battleship",self);
        List<String>submarineLocations = Util.getLocatiosByType("submarine",self);
        List<String>destroyerLocations = Util.getLocatiosByType("destroyer",self);
        List<String>patrolboatLocations = Util.getLocatiosByType("patrolboat",self);

        //Damage total
        long carrierDamage = 0;
        long battleShipDamage = 0;
        long submarineDamage = 0;
        long destroyerDamage = 0;
        long patrolboatDamage = 0;

        for(Salvo salvoShot: Util.getOpponent(self).get().getSalvo()){
            //Missed shots
            long missedShot = salvoShot.getLocations().size();
            //Damage Per Turn
            long carrierHitsInTurn = 0;
            long battleShipHitsInTurn = 0;
            long submarineHitsInTurn = 0;
            long destroyerHitsInTurn = 0;
            long patrolboatHitsInTurn = 0;

            for(String location: salvoShot.getLocations()){

                if (carrierLocations.contains(location)){
                    hitCellsList.add(location);
                    carrierDamage++;
                    carrierHitsInTurn++;
                    missedShot--;
                }

                if (battleshipLocations.contains(location)){
                    hitCellsList.add(location);
                    battleShipDamage++;
                    battleShipHitsInTurn++;
                    missedShot--;
                }

                if (submarineLocations.contains(location)){
                    hitCellsList.add(location);
                    submarineDamage++;
                    submarineHitsInTurn++;
                    missedShot--;
                }

                if (destroyerLocations.contains(location)){
                    hitCellsList.add(location);
                    destroyerDamage++;
                    destroyerHitsInTurn++;
                    missedShot--;
                }

                if (patrolboatLocations.contains(location)){
                    hitCellsList.add(location);
                    patrolboatDamage++;
                    patrolboatHitsInTurn++;
                    missedShot--;
                }
            }
            //Damages
            damagePerTurn.put("carrierHits",carrierHitsInTurn);
            damagePerTurn.put("battleShipHits",battleShipHitsInTurn);
            damagePerTurn.put("submarineHits",submarineHitsInTurn);
            damagePerTurn.put("destroyerHits",destroyerHitsInTurn);
            damagePerTurn.put("patrolboatHits",patrolboatHitsInTurn);
            damagePerTurn.put("carrier",carrierDamage);
            damagePerTurn.put("battleShip",battleShipDamage);
            damagePerTurn.put("submarine",submarineDamage);
            damagePerTurn.put("destroyer",destroyerDamage);
            damagePerTurn.put("patrolboat",patrolboatDamage);

            hitsMapPerTurn.put("turn",salvoShot.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damagePerTurn);
            hitsMapPerTurn.put("missed",missedShot);
        }
        hits.add(hitsMapPerTurn);
        return hits;
    }

    public List<Map<String, Object>> makeOpponentHitsDTO(GamePlayer opponent){
        List<Map<String, Object>>hits = new ArrayList<>();
        Map<String, Object>hitsMapPerTurn = new LinkedHashMap<>();
        Map<String,Object>damagePerTurn= new LinkedHashMap<>();
        List<String>hitCellsList = new ArrayList<>();

        //Types of Ships
        List<String>carrierLocations = Util.getLocatiosByType("carrier",opponent);
        List<String>battleshipLocations = Util.getLocatiosByType("battleship",opponent);
        List<String>submarineLocations = Util.getLocatiosByType("submarine",opponent);
        List<String>destroyerLocations = Util.getLocatiosByType("destroyer",opponent);
        List<String>patrolboatLocations = Util.getLocatiosByType("patrolboat",opponent);

        //Damage total
        long carrierDamage = 0;
        long battleShipDamage = 0;
        long submarineDamage = 0;
        long destroyerDamage = 0;
        long patrolboatDamage = 0;


        for(Salvo salvoShot: Util.getOpponent(opponent).get().getSalvo()){
           //Missed Shots
            long missedShot = salvoShot.getLocations().size();
            //Damage Per Turn
            long carrierHitsInTurn = 0;
            long battleShipHitsInTurn = 0;
            long submarineHitsInTurn = 0;
            long destroyerHitsInTurn = 0;
            long patrolboatHitsInTurn = 0;
            for(String location: salvoShot.getLocations()){

                if (carrierLocations.contains(location)){
                    hitCellsList.add(location);
                    carrierDamage++;
                    carrierHitsInTurn++;
                    missedShot--;
                }

                if (battleshipLocations.contains(location)){
                    hitCellsList.add(location);
                    battleShipDamage++;
                    battleShipHitsInTurn++;
                    missedShot--;
                }

                if (submarineLocations.contains(location)){
                    hitCellsList.add(location);
                    submarineDamage++;
                    submarineHitsInTurn++;
                    missedShot--;
                }

                if (destroyerLocations.contains(location)){
                    hitCellsList.add(location);
                    destroyerDamage++;
                    destroyerHitsInTurn++;
                    missedShot--;
                }

                if (patrolboatLocations.contains(location)){
                    hitCellsList.add(location);
                    patrolboatDamage++;
                    patrolboatHitsInTurn++;
                    missedShot--;
                }
            }
            //Damages
            damagePerTurn.put("carrierHits",carrierHitsInTurn);
            damagePerTurn.put("battleShipHits",battleShipHitsInTurn);
            damagePerTurn.put("submarineHits",submarineHitsInTurn);
            damagePerTurn.put("destroyerHits",destroyerHitsInTurn);
            damagePerTurn.put("patrolboatHits",patrolboatHitsInTurn);
            damagePerTurn.put("carrier",carrierDamage);
            damagePerTurn.put("battleShip",battleShipDamage);
            damagePerTurn.put("submarine",submarineDamage);
            damagePerTurn.put("destroyer",destroyerDamage);
            damagePerTurn.put("patrolboat",patrolboatDamage);

            hitsMapPerTurn.put("turn",salvoShot.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damagePerTurn);
            hitsMapPerTurn.put("missed",missedShot);
        }
        
        hits.add(hitsMapPerTurn);
        return hits;
    }

}





