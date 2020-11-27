package com.codeoftheweb.salvo.model;

import com.codeoftheweb.salvo.model.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    public Player(){}
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

    public String toString(){
        return name + " " + email;
    }

    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayers;
    }

    public void setGamePlayerSet(Set<GamePlayer> gamePlayerSet) {
        this.gamePlayers = gamePlayerSet;
    }
}

