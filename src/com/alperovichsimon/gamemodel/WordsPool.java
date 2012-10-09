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

    private List<String> pool = new ArrayList<String>();
    private Map<String, Boolean> guessed = new HashMap<String, Boolean>();
    private List<Integer> indexes = new ArrayList<Integer>();
    private int curIndex = 0;

    private WordsPool() {

    }

    public void addWord(String word){
        pool.add(word);
        guessed.put(word, false);
        indexes.add(pool.size());
    }

    public void wordGuessed(String word){
        if (!guessed.containsKey(word)){
            Logger.log("No such word: "+ word);
        }
        guessed.put(word, true);
    }

    public void shuffle(){
        Collections.shuffle(indexes);
        curIndex = 0;
    }

    public String getNextWord(){
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



    public static synchronized WordsPool getInstance() {
        if (INSTANCE == null){
            INSTANCE = new WordsPool();
        }
        return INSTANCE;
    }
}
