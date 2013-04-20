package com.alperovichsimon.persistence;

import android.content.res.AssetManager;
import android.util.Log;
import com.alperovichsimon.gamemodel.Word;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Сеня
 * Date: 21.04.13
 * Time: 0:51
 * To change this template use File | Settings | File Templates.
 */
public class WordsPersistenceManager {
    private static WordsPersistenceManager INSTANCE;
    private static final String filename = "wordsCatalog.xml";
    private static final String TAG = "WordsPersistenceManager";
    private boolean loaded = false;
    private SAXParserFactory factory;
    private SAXParser parser;
    private AssetManager assetManager;

    private List<String> hardWords;
    private List<String> mediumWords;
    private List<String> easyWords;
    private List<String> currentArray;

    private int hardNotAdd;
    private int mediumNotAdd;
    private int easyNotAdd;
    private int curNotAdd;



    private WordsPersistenceManager() {
    }

    public static synchronized WordsPersistenceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WordsPersistenceManager();
        }
        return INSTANCE;
    }

    public void loadWords(AssetManager manager) {
        if (!loaded) {
            try {
                easyWords = new ArrayList<String>();
                mediumWords = new ArrayList<String>();
                hardWords = new ArrayList<String>();
                assetManager = manager;
                factory = SAXParserFactory.newInstance();
                parser = factory.newSAXParser();
                parser.parse(assetManager.open(filename), new MyDefaultHandler());
            } catch (Exception e) {
                Log.d(TAG, "I can't read words from the file", e);
            }

        }
    }

    public void shuffle() {
        if (loaded) {
            currentArray = null;
            curNotAdd = 0;
            hardNotAdd = hardWords.size();
            mediumNotAdd = mediumWords.size();
            easyNotAdd = easyWords.size();
        }
    }

    public String getNextWord(Word.Level level) {
        if (!loaded) {
            return null;
        }
        if (level == Word.Level.HARD) {
            currentArray = hardWords;
            curNotAdd = hardNotAdd;
        } else if (level == Word.Level.MEDIUM) {
            currentArray = mediumWords;
            curNotAdd = mediumNotAdd;

        } else if (level == Word.Level.EASY) {
            currentArray = easyWords;
            curNotAdd = easyNotAdd;
        }
        if (curNotAdd == 0) {
            return null;
        }
        int curIndex = new Random().nextInt(curNotAdd);


        Collections.swap(currentArray, curIndex, curNotAdd - 1);
        if (level == Word.Level.HARD) {
            hardNotAdd --;
        } else if (level == Word.Level.MEDIUM) {
            mediumNotAdd--;

        } else if (level == Word.Level.EASY) {
            easyNotAdd--;
        }
        return currentArray.get(curIndex);
    }

    private class MyDefaultHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("hard")) {
                currentArray = hardWords;
            } else if (qName.equals("medium")) {
                currentArray = mediumWords;
            } else if (qName.equals("easy")) {
                currentArray = easyWords;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            currentArray.add(new String(ch, start, length));
        }

        @Override
        public void startDocument() throws SAXException {
            Log.d(TAG, "start reading " + filename);

        }

        @Override
        public void endDocument() throws SAXException {
            loaded = true;
            shuffle();
            Log.d(TAG, "end reading " + filename);
        }
    }
}
