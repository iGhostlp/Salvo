package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ShipController {
    //Repos
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    //Place ships
    @RequestMapping(value = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
     public ResponseEntity<Map<String, Object>> addShip(@PathVariable long gamePlayerId, @RequestBody List<Ship> shipList, Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not logged in"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        Long idGamePlayer= gamePlayer.getId();

        if(idGamePlayer ==null){
            return new ResponseEntity<>(Util.makeMap("error", "GamePlayer Does Not Exists"), HttpStatus.UNAUTHORIZED);
        }

        if (player.getId() != gamePlayer.getPlayer().getId()) {
            return new ResponseEntity<>(Util.makeMap("error", "This is not your board, stay back"), HttpStatus.UNAUTHORIZED);

        }

        if (gamePlayer.getShips().size() >= 5) {
            return new ResponseEntity<>(Util.makeMap("error", "All Ships Already Placed"), HttpStatus.FORBIDDEN);
        }

            List <Ship> newShipList = shipList.stream()
                                          .map(ship ->{ ship.setGamePlayer(gamePlayer);return ship;})
                                          .collect(Collectors.toList());

            shipRepository.saveAll(newShipList);
            return new ResponseEntity<>(Util.makeMap("OK", "Ship Placed"),HttpStatus.CREATED);

        }

}


