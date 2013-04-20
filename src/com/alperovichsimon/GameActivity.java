package com.alperovichsimon;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.alperovichsimon.gamemodel.*;


/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 10.04.13
 * Time: 1:33
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity   {

    ViewFlipper page;
    private Button guessedWordButton;
    private Button popWordButton;
    private Button pushWordButton;
    private TextView timerLabel;
    private TextView currentWordLabel;


    private CountDownTimer roundTimer;

    private Team currentTeamPlaying;
    private Word currentWord;
    private boolean isPlaying;
    private int roundNumber = 1;
    //TODO: extract to another class
    private final int roundLength = 15;

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if((e1.getY() - e2.getY()) > sensitvity){
                popWord();
            }else if((e2.getY() - e1.getY()) > sensitvity){
                finishRound();
            }

            return true;
        }

    };

    private GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);

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

        initialize();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }




    private void startRound()
    {

        startTimer();
    }

    private void finishRound()
    {
        stopTimer();
        currentWordLabel.setText("");

        popWordButton.setEnabled(true);
        pushWordButton.setEnabled(false);
        guessedWordButton.setEnabled(false);

        isPlaying = false;
        currentTeamPlaying = TeamPool.getInstance().getNextTeamPlaying(roundNumber++);
    }

    private void finishGame()
    {
        stopTimer();
        currentWordLabel.setText("Игра окончена! Идите нахуй!");
    }

    private void initialize()
    {
        Team a = new Team("pidorki");
        a.addPlayer(new Player("nazarov"));
        Team b = new Team("gei");
        b.addPlayer(new Player("alperovich"));

        TeamPool.getInstance().addTeam(a);
        TeamPool.getInstance().addTeam(b);
    }

    private void popWord()
    {
        currentWord  = WordsPool.getInstance().getNextWord();

        if (currentWord == null) {
            finishGame();
        }

        currentWordLabel.setText(currentWord.getValue());
        popWordButton.setEnabled(false);
        pushWordButton.setEnabled(true);
        guessedWordButton.setEnabled(true);
    }

    public void popWordFromHat(View view)
    {
        isPlaying = true;
        popWord();
        startTimer();
    }

    public void pushWordButtonClick(View view)
    {
        finishRound();
    }

    public void guessedWordButtonClick(View view)
    {
        WordsPool.getInstance().wordGuessed();
        //currentTeamPlaying.addGuessedWord(currentWord);

        currentWord  = WordsPool.getInstance().getNextWord();
        if (currentWord != null)
            currentWordLabel.setText(currentWord.getValue());
        else
        {
            finishGame();
        }
    }

    public void startTimer() {
        roundTimer = new CountDownTimer(roundLength * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLabel.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerLabel.setText("Раунд закончен!");
                finishRound();
            }
        };
        timerLabel.setText(roundLength + "s");
        roundTimer.start();
        isPlaying = true;
    }

    public void stopTimer() {
        if(roundTimer != null)
            roundTimer.cancel();
        timerLabel.setText("Раунд закончен!");
        isPlaying = false;
    }
}
