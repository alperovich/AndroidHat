package com.alperovichsimon.gamemodel;

import com.alperovichsimon.logger.Logger;

import java.util.*;

/**
 * Simon Alperovich
 * Date: 27.09.12
 * Time: 2:55
 */
public class WordsPool {
    private static WordsPool INSTANCE;

    private ArrayList<Word> pool = new ArrayList<Word>();
    private Map<Word, Boolean> guessed = new HashMap<Word, Boolean>();
    private List<Integer> indexes = new ArrayList<Integer>();
    private int curIndex = 0;
    //TODO: все связанное со сложностью слов вынести в класс Word
    private int hard;
    private int medium;
    private int easy;

    private WordsPool() {

    }

    public void addWord(Word word){
        pool.add(word);
        guessed.put(word, false);
        indexes.add(pool.size());
    }

    public void addWord(Word word, Level level){
        addWord(word);
        switch (level){
            case HARD:
                ++hard;
                break;
            case MEDIUM:
                ++medium;
                break;
            case EASY:
                ++easy;
        }
    }

    public int getHardNumber(){
        return hard;
    }

    public int getMediumNumber(){
        return medium;
    }

    public int getEasyNumber(){
        return easy;
    }

    public void wordGuessed(Word word){
        if (!guessed.containsKey(word)){
            Logger.log("No such word: "+ word);
        }
        guessed.put(word, true);
    }

    public void shuffle(){
        Collections.shuffle(indexes);
        curIndex = 0;
    }

    public Word getNextWord(){
        int size = pool.size();
        while (!guessed.get(pool.get(indexes.get(curIndex))) && curIndex < size){
            ++curIndex;
        }
        if (curIndex >= size){
            return null;
        }
        return pool.get(indexes.get(curIndex));
    }

    public int getWordsNumber(){
        return pool.size();
    }

    public void deleteAll() {
        pool.clear();
        guessed.clear();
        indexes.clear();
        curIndex = 0;
        hard = 0;
        medium = 0;
        easy = 0;
    }

    public static synchronized WordsPool getInstance() {
        if (INSTANCE == null){
            INSTANCE = new WordsPool();
        }
        return INSTANCE;
    }

    public enum Level{
        HARD,
        MEDIUM,
        EASY
    }
}
