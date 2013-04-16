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
import com.alperovichsimon.gamemodel.Team;
import com.alperovichsimon.gamemodel.TeamPool;
import com.alperovichsimon.gamemodel.Word;
import com.alperovichsimon.gamemodel.WordsPool;


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


    private CountDownTimer _roundTimer;

    private Team currentTeamPlaying;
    private Word currentWord;
    private boolean isPlaying;
    private int roundNumber = 1;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if((e1.getY() - e2.getY()) > sensitvity){
                      popWord();
            }else if((e2.getY() - e1.getY()) > sensitvity){
                FinishRound();
            }

            return true;
        }

    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);


    private void StartRound()
    {

        startTimer();
    }

    private void FinishRound()
    {
        stopTimer();
        currentWordLabel.setText("");

        popWordButton.setEnabled(true);
        pushWordButton.setEnabled(false);
        guessedWordButton.setEnabled(false);

        isPlaying = false;
        currentTeamPlaying = TeamPool.getInstance().getNextTeamPlaying(roundNumber++);
    }

    private void FinishGame()
    {
        stopTimer();
        currentWordLabel.setText("Игра окончена! Идите нахуй!");
    }

    private void Initialize ()
    {
        Team a = new Team("pidorki");
        a.addPlayer("nazarov");
        Team b = new Team("gei");
        b.addPlayer("alperovich");

        TeamPool.getInstance().addTeam(a);
        TeamPool.getInstance().addTeam(b);

        Word word1 = new Word("ololo");
        Word word2 = new Word("ahahha");
        WordsPool.getInstance().addWord(word1);
        WordsPool.getInstance().addWord(word2);
    }

    private void popWord()
    {
        currentWord  = WordsPool.getInstance().getNextWord();

        if (currentWord == null) {
            FinishGame();
        }

        currentWordLabel.setText(currentWord.getValue());
        popWordButton.setEnabled(false);
        pushWordButton.setEnabled(true);
        guessedWordButton.setEnabled(true);
    }

    public void PopWordFromHat (View view)
    {
        isPlaying = true;
        popWord();
        startTimer();
    }

    public void PushWordButtonClick (View view)
    {
        FinishRound();
    }

    public void GuessedWordButtonClick (View view)
    {
        WordsPool.getInstance().wordGuessed();
        //currentTeamPlaying.addGuessedWord(currentWord);

        currentWord  = WordsPool.getInstance().getNextWord();
        if (currentWord != null)
            currentWordLabel.setText(currentWord.getValue());
        else
        {
            FinishGame();
        }
    }

    public void startTimer() {
        _roundTimer = new CountDownTimer(roundLength * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLabel.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerLabel.setText("Раунд закончен!");
                FinishRound();
            }
        };
        timerLabel.setText(roundLength + "s");
        _roundTimer.start();
        isPlaying = true;
    }

    public void stopTimer() {
        if(_roundTimer != null)
            _roundTimer.cancel();
        timerLabel.setText("Раунд закончен!");
        isPlaying = false;
    }
}
