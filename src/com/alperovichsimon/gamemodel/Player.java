package com.alperovichsimon.gamemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Simon Alperovich
 * Date: 21.04.13
 * Time: 0:27
 */

public class Player {
  private final List<Word> guessedWords = new ArrayList<Word>();
  private final List<Word> offeredWords = new ArrayList<Word>();
  private String name;

  public Player(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void guessedWord(Word word) {
    guessedWords.add(word);
  }

  public void offeredWord(Word word) {
    offeredWords.add(word);
  }

  public List<Word> guessedWords() {
    return new ArrayList<Word>(guessedWords);
  }

  public List<Word> offeredWords() {
    return new ArrayList<Word>(offeredWords);
  }
}
