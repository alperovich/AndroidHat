package com.alperovichsimon.gamemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 10.04.13
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */
public class Team {
    private List<String> _players = new ArrayList<String>();
    private List<Word> _guessedWords = new ArrayList<Word>();
    private String _name;

    public Team (String name)
    {
        _name = name;
    }

    public void addPlayer (String playerName)
    {
        _players.add(playerName);
    }

    public void addGuessedWord (Word word)
    {
        _guessedWords.add(word);
    }

    public int guessedWordsCount ()
    {
        return _guessedWords.size();
    }
}
