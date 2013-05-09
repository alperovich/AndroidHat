package com.alperovichsimon.gamemodel;

import java.util.ArrayList;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: Сеня
* Date: 21.04.13
* Time: 0:27
* To change this template use File | Settings | File Templates.
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

    public void guessedWord(Word word){
        guessedWords.add(word);
    }
    public void offeredWord(Word word){
        offeredWords.add(word);
    }
}
