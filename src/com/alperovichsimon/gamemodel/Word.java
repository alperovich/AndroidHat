package com.alperovichsimon.gamemodel;

/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 10.04.13
 * Time: 2:25
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private String value;
    private Level level;


    public Word(String value)
    {
        this.value = value;
        this.level = Level.EASY;
    }

    public Word(String value, Level level)
    {
        this.value = value;
        this.level = level;
    }

    public String getValue()
    {
        return value;
    }

    public Level getLevel() {
        return level;
    }


    public enum Level{
        HARD,
        MEDIUM,
        EASY
    }
}
