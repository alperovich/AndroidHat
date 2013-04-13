package com.alperovichsimon.gamemodel;

import com.alperovichsimon.logger.Logger;

import java.util.*;

/**
 * Simon Alperovich
 * Date: 27.09.12
 * Time: 2:30
 */
public class TeamPool {
    private static TeamPool INSTANCE;

    private Map<Team, Integer> guessedNumber = new LinkedHashMap<Team, Integer>();
    private List<Team> pool = new ArrayList<Team>();

    public Team getNextTeamPlaying(int number)
    {
        return pool.get( (number - 1 ) % pool.size());
    }

    private TeamPool() {

    }

    public static synchronized TeamPool getInstance() {
        if (INSTANCE == null){
            INSTANCE  = new TeamPool();
        }
        return INSTANCE;
    }

    public void addTeam(Team team) {
        pool.add(team);
        guessedNumber.put(team, 0);
    }

    public List<Team> getPool(){
        return pool;
    }

   /* public void playerGuessedWord(Team team) {
        if (!guessedNumber.containsKey(team)) {
            Logger.log("no such player: " + team + "in pool");
        }
        guessedNumber.put(team, guessedNumber.get(team) + 1);
    }

    public int getNumberOfGuessedWords(Player player) {
        if (!guessedNumber.containsKey(player)) {
            Logger.log("no such player: " + player + "in pool");
            return 0;
        }
        return guessedNumber.get(player);
    }                  */

    public int getTeamsNumber(){
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
