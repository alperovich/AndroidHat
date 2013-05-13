package com.alperovichsimon.gamemodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 10.04.13
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */
public class Team {
  private List<Player> players = new ArrayList<Player>();
  private List<Word> guessedWords = new ArrayList<Word>();
  private String name;
  private static final String TAG = "TEAM";
  private int teamPlayed = 0;

  public Team(String name) {
    this.name = name;
  }

  public void roundFinished() {
    teamPlayed++;
  }

  public Player currentSpeaker() {
    return players.get((teamPlayed) % players.size());
  }

  public Player currentListener() {
    return players.get((teamPlayed + 1) % players.size());
  }

  public String teamNames() {
    String str = "";
    for (Player player : players) {
      str += player + " и ";
    }
    return str.substring(0, str.length() - 3);
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void addPlayers(Player... players) {
    this.players.addAll(Arrays.asList(players));
  }

  public void wordGuessed(Word word) {
    guessedWords.add(word);
    currentListener().guessedWord(word);
    currentSpeaker().offeredWord(word);
  }

  public int guessedWordsCount() {
    return guessedWords.size();
  }

  public boolean hasPlayer(Player player) {
    return players.contains(player);
  }

  public List<Player> getPlayers() {
    return new ArrayList<Player>(players);
  }

}
