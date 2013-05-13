package com.alperovichsimon.gamemodel;

import java.util.*;

/**
 * Simon Alperovich
 * Date: 27.09.12
 * Time: 2:30
 */
public class TeamPool {
  private static TeamPool INSTANCE;

  private List<Team> pool = new ArrayList<Team>();

  public Team getNextTeamPlaying(int number) {
    return pool.get((number - 1) % pool.size());
  }

  private TeamPool() {

  }

  public static synchronized TeamPool getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TeamPool();
    }
    return INSTANCE;
  }

  public void addTeam(Team team) {
    pool.add(team);
  }

  public List<Team> getPool() {
    return pool;
  }

  public int getTeamsNumber() {
    return pool.size();
  }

}
