package com.alperovichsimon.gamemodel;

import android.util.Log;

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

  public Team(String name) {
    this.name = name;
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void addPlayers(Player... players) {
    this.players.addAll(Arrays.asList(players));
  }

  public void wordGuessed(Word word, Player playerWhichGuessed, Player playerWhichOffered) {
    if (!hasPlayer(playerWhichGuessed)) {
      Log.v(TAG, "Team " + name + "doesn't have player " + playerWhichGuessed);
      return;
    }
    if (!hasPlayer(playerWhichOffered)) {
      Log.v(TAG, "Team " + name + "doesn't have player " + playerWhichOffered);
      return;
    }
    guessedWords.add(word);
    playerWhichGuessed.guessedWord(word);
    playerWhichOffered.offeredWord(word);
  }

  public int guessedWordsCount() {
    return guessedWords.size();
  }

  public boolean hasPlayer(Player player) {
    return players.contains(player);
  }

}
