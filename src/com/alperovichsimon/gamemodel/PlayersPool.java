package com.alperovichsimon.gamemodel;

import com.alperovichsimon.logger.Logger;

import java.util.*;

/**
 * Simon Alperovich
 * Date: 27.09.12
 * Time: 2:30
 */
public class PlayersPool {
    private static PlayersPool INSTANCE;
                 private int _a;
    private Map<Player, Integer> guessedNumber = new LinkedHashMap<Player, Integer>();
    private List<Player> pool = new ArrayList<Player>();
    private int kkk=0;


    private PlayersPool() {

    }

    public static synchronized PlayersPool getInstance() {
        if (INSTANCE == null){
            INSTANCE  = new PlayersPool();
        }
        return INSTANCE;
    }

    public void addPlayer(Player player) {
        pool.add(player);
        guessedNumber.put(player, 0);
    }

    public List<Player> getPool(){
        return pool;
    }

    public void playerGuessedWord(Player player) {
        if (!guessedNumber.containsKey(player)) {
            Logger.log("no such player: " + player + "in pool");
        }
        guessedNumber.put(player, guessedNumber.get(player) + 1);
    }

    public int getNumberOfGuessedWords(Player player) {
        if (!guessedNumber.containsKey(player)) {
            Logger.log("no such player: " + player + "in pool");
            return 0;
        }
        return guessedNumber.get(player);
    }

    public int getPlayersNumber(){
        return pool.size();
    }


    private static class Player {
        private Player partner;
        private String name;

        public Player() {

        }


        public Player(Player partner) {
            if (partner.hasPartner()) {
                Logger.log("Player " + partner + "already has partner" + partner.getPartner());
            }

            this.partner = partner;
            partner.partner = this;

        }

        public boolean hasPartner() {
            return partner != null;
        }

        public String toString() {
            return name;
        }

        public void setPartner(Player partner) {
            this.partner = partner;
        }

        public Player getPartner() {
            return this.partner;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
