package com.codeoftheweb.salvo.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private  double score;
    private LocalDateTime finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    @OrderBy
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerID")
    @OrderBy
    private Player player;


    public Score(){}

    public Score(double score, LocalDateTime finishDate, Game game, Player player) {
        this.score = score;
        this.finishDate = finishDate;
        this.game = game;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
