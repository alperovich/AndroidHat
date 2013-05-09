package com.alperovichsimon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class GameActivity extends Activity {

    ViewFlipper page;
    private Button popWordButton;
    private Button pushWordButton;
    private TextView timerLabel;
    private TextView textInfo;
    private TextView currentWordLabel;


    private CountDownTimer roundTimer;

    private Team currentTeamPlaying;
    private Word currentWord;
    private boolean isPlaying;
    private int roundNumber = 1;
    //TODO: extract to another class
    private final int roundLength = 15;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        popWordButton = (Button) findViewById(R.id.pop_word_button);
        pushWordButton = (Button) findViewById(R.id.push_word_button);
        timerLabel = (TextView) findViewById(R.id.timer);
        currentWordLabel = (TextView) findViewById(R.id.current_word);
        textInfo = (TextView) findViewById(R.id.textInfo);



        initialize();
        update();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if ((e1.getY() - e2.getY()) > sensitvity) {
                handlePopWord();
            } else if ((e2.getY() - e1.getY()) > sensitvity) {
                handlePushWord();
            }

            return true;
        }
    };

    private GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);


    private void update(){
        currentTeamPlaying = TeamPool.getInstance().getNextTeamPlaying(roundNumber);

        timerLabel.setText(roundLength + "s");
        textInfo.setText(currentTeamPlaying.currentSpeaker() + " -> " + currentTeamPlaying.currentListener());
        currentWordLabel.setText("");

        setButtonsEnabled(true, false);
        popWordButton.setText("Достать слово из шляпы");
    }

    private void startRound() {
        isPlaying = true;
        popWord();
        startTimer();
    }

    private void finishRound() {
        roundNumber++;
        isPlaying = false;
        currentTeamPlaying.roundFinished();
        update();
        dialog("Раунд закончен, а тем временем хуи сосут " + currentTeamPlaying.teamNames());
    }

    private void finishGame() {
        stopTimer();
        dialog("Игра окончена, идите нахуй!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(GameActivity.this, StatsActivity.class);
                GameActivity.this.startActivity(intent);
            }
        });
    }

    private void initialize() {
        Team a = new Team("pidorki");
        a.addPlayer(new Player("Таланов"));
        a.addPlayer(new Player("Назаров"));
        Team b = new Team("gei");
        b.addPlayer(new Player("Альперович"));
        b.addPlayer(new Player("Пел"));

        TeamPool.getInstance().addTeam(a);
        TeamPool.getInstance().addTeam(b);

        WordsPool.getInstance().addWord(new Word("shalava"));
        WordsPool.getInstance().addWord(new Word("olollo"));
        WordsPool.getInstance().addWord(new Word("pushka"));
        WordsPool.getInstance().addWord(new Word("petrushka"));
    }


    private void dialog(String message) {
        dialog(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void dialog(String message, DialogInterface.OnClickListener listener) {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false); // This blocks the 'BACK' button
        ad.setMessage(message);
        ad.setButton("OK", listener);
        ad.show();
    }

    private void popWord() {
        currentWord = WordsPool.getInstance().getNextWord();

        if (currentWord == null) {
            finishGame();
            return;
        }

        currentWordLabel.setText(currentWord.getValue());
    }

    private void setButtonsEnabled(boolean popWord, boolean pushWord) {
        popWordButton.setEnabled(popWord);
        pushWordButton.setEnabled(pushWord);
    }

    private void handlePopWord()
    {
        if (!isPlaying) {
            popWordButton.setText("Слово угадано");
            setButtonsEnabled(true, true);
            startRound();
        } else {
            WordsPool.getInstance().wordGuessed();
            currentTeamPlaying.wordGuessed(currentWord);

            currentWord = WordsPool.getInstance().getNextWord();
            if (currentWord != null)
                currentWordLabel.setText(currentWord.getValue());
            else {
                finishGame();
            }
        }
    }

    public void popWordFromHat(View view) {
         handlePopWord();
    }

    public void pushWordButtonClick(View view) {
        handlePushWord();
    }

    private void handlePushWord() {
        finishRound();
        stopTimer();
    }

    public void startTimer() {
        roundTimer = new CountDownTimer(roundLength * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLabel.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                finishRound();
            }
        };
        timerLabel.setText(roundLength + "s");
        roundTimer.start();
        isPlaying = true;
    }

    public void stopTimer() {
        if (roundTimer != null)
            roundTimer.cancel();
        isPlaying = false;
    }
}
