package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class GamePlayer {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerID")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    private Game game;

    @OrderBy
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private Set<Ship> ship;

    @OrderBy
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private Set<Salvo> salvo;


    public GamePlayer() {
        this.ship = new HashSet<Ship>();
        this.salvo = new HashSet<Salvo>();

        //OJO
        this.game = new Game();
    }

    public GamePlayer(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.joinDate = new Date();
        this.ship = new HashSet<Ship>();
        this.salvo = new HashSet<Salvo>();
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void addShip(Ship newShip) {
        this.ship.add(newShip);
        newShip.setGamePlayer(this);
    }

    public Set<Ship> getShips() {
        return ship;
    }

    public void setShip(Set<Ship> ship) {
        this.ship = ship;
    }

    public Set<Salvo> getSalvo() {
        return salvo;
    }

    public void setSalvo(Set<Salvo> salvo) {
        this.salvo = salvo;
    }

    public void addSalvo(Salvo newSalvo) {
        this.salvo.add(newSalvo);
        newSalvo.setGamePlayer(this);
    }

    public GamePlayer getOpponent() {
        return this.getGame().getGamePlayers()
                .stream().filter(gamePlayer -> gamePlayer.getId() != this.getId())
                .findFirst()
                .orElse(null);
    }
}






