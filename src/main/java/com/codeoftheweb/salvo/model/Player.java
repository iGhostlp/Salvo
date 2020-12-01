package com.codeoftheweb.salvo.model;

import com.codeoftheweb.salvo.model.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;


    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    public Player() {
    }

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return name + " " + email;
    }

    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayers;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayers = gamePlayerSet;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }


    //Scores
    public double getTotalScore() {
        return getWinScore() * 1.00 + getDrawScore() * 0.50 + getLoseScore() * 0.00;
    }

    public long getWinScore() {
        return this.getScores().stream()
                .filter(score -> score.getScore() == 1.00)
                .count();
    }

    public long getDrawScore() {
        return this.getScores().stream()
                .filter(score -> score.getScore() == 0.50)
                .count();
    }

    public long getLoseScore() {
        return this.getScores().stream()
                .filter(score -> score.getScore() == 0.00)
                .count();
    }

    public Score getGameScore(Game game) {
        return getScores().stream()
                .filter(score -> score.getGame().getId() == game.getId()).findFirst().orElse(null);
    }
}

