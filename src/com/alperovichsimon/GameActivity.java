package com.alperovichsimon;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alperovichsimon.gamemodel.Team;
import com.alperovichsimon.gamemodel.Word;
import com.alperovichsimon.gamemodel.WordsPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 10.04.13
 * Time: 1:33
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {
    private List<Team>  _teams = new ArrayList<Team>();
    private LinkedList<Word> _words = new LinkedList<Word>();

    private Button guessedWordButton;
    private Button popWordButton;
    private Button pushWordButton;
    private TextView timerLabel;
    private TextView currentWordLabel;


    private CountDownTimer _roundTimer;

    private Team currentTeamPlaying;
    private Word currentWord;
    private boolean isPlaying;
    //TODO: extract to another class
    private int roundLength = 15;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);



        guessedWordButton = (Button) findViewById(R.id.guessed_word_button);
        popWordButton = (Button) findViewById(R.id.pop_word_button);
        pushWordButton = (Button) findViewById(R.id.push_word_button);
        timerLabel = (TextView) findViewById(R.id.timer);
        currentWordLabel = (TextView) findViewById(R.id.current_word);

        pushWordButton.setEnabled(false);
        guessedWordButton.setEnabled(false);

        Initialize();
    }

    private void Initialize ()
    {
        Team a = new Team();
        a.addPlayer("nazarov");
        Team b = new Team();
        b.addPlayer("alperovich");

        Word first = new Word("hui");
        _words.add(first);
        _teams.add(a);
        _teams.add(b);
        currentTeamPlaying = a;
    }

    public void PopWordFromHat (View view)
    {
        currentWord = _words.get(_words.size() - 1);
        String word  = WordsPool.getInstance().getNextWord();


        currentWordLabel.setText(currentWord.getWord());
        popWordButton.setEnabled(false);
        pushWordButton.setEnabled(true);
        guessedWordButton.setEnabled(true);

        startTimer();
    }

    public void PushWordButtonClick (View view)
    {
        stopTimer();
        currentWordLabel.setText("");
        popWordButton.setEnabled(true);
        pushWordButton.setEnabled(false);
        guessedWordButton.setEnabled(false);
    }

    public void startTimer() {
        // Create a new CountDownTimer to track the brew time
        _roundTimer = new CountDownTimer(roundLength * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLabel.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                isPlaying = false;
                timerLabel.setText("Раунд закончен!");
            }
        };

        _roundTimer.start();
        isPlaying = true;
    }

    public void stopTimer() {
        if(_roundTimer != null)
            _roundTimer.cancel();

        isPlaying = false;
    }
}
