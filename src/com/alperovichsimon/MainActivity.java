package com.alperovichsimon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alperovichsimon.gamemodel.TeamPool;
import com.alperovichsimon.gamemodel.WordsPool;

public class MainActivity extends Activity {
  private TextView wordsCounter;
  private TextView playersCounter;
  private static final String TAG = "MainActivity";

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);


  }

  public void onStart() {
    super.onStart();
    wordsCounter = (TextView) findViewById(R.id.word_counter_text);
    playersCounter = (TextView) findViewById(R.id.player_counter_text);
    prepare();
    update();
  }

  private void prepare() {
    final Button wordsButton = (Button) findViewById(R.id.edit_words_button);
    final Button playersButton = (Button) findViewById(R.id.edit_players_button);
    wordsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, WordsActivity.class);
        MainActivity.this.startActivity(intent);
      }
    });

    playersButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, PlayersActivity.class);
        MainActivity.this.startActivity(intent);
      }
    });
  }

  private void update() {
    wordsCounter.setText(getString(R.string.word_counter_text) + " " + WordsPool.getInstance().getWordsNumber());
    playersCounter.setText(getString(R.string.player_counter_text) + " " + TeamPool.getInstance().getTeamsNumber());
  }

  public void newGameButtonClick(View view) {
    Intent intent = new Intent(MainActivity.this, GameActivity.class);
    MainActivity.this.startActivity(intent);
  }

}
