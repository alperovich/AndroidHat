package com.alperovichsimon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.alperovichsimon.gamemodel.Player;
import com.alperovichsimon.gamemodel.Team;
import com.alperovichsimon.gamemodel.TeamPool;

public class PlayersActivity extends Activity {

  private Button addPlayerButton;
  private EditText playerText;
  private ListView teamListView;

  private void addPlayer(String name) {
    //TODO: right thing
    assert (isValid(name));
    Player newPlayer = new Player(name);
    TeamPool teamPool = TeamPool.getInstance();
    Team newTeam = new Team("Team #" + teamPool.getTeamsNumber());
    newTeam.addPlayer(newPlayer);
    teamPool.addTeam(newTeam);
  }

  private boolean isValid(String name) {
    //TODO: Check for uniqueness
    return name.length() < 20;
  }

  private void fireNotValidNameError() {
    //TODO: make separate class for alerts
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    alertDialogBuilder.setTitle("Ошибка");

    alertDialogBuilder.setMessage("Имя было введено неверно, попробуйте еще раз.");
    alertDialogBuilder.setCancelable(false);
    alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.players);
  }

  @Override
  public void onStart() {
    super.onStart();
    prepare();
    update();
  }

  private void prepare() {
    addPlayerButton = (Button) findViewById(R.id.add_player_button);
    playerText = (EditText) findViewById(R.id.add_player_edittext);
    teamListView = (ListView) findViewById(R.id.team_list_view);
    displayTeamListView();
    addPlayerButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        if (isValid(playerText.getText().toString())) {
          addPlayer(playerText.getText().toString());
          update();
        } else {
          fireNotValidNameError();
        }
      }
    });
  }

  private void displayTeamListView() {
    teamListView.setAdapter(dataAdapter);
  }

  private void update() {
    playerText.setText("");
    displayTeamListView();
//    hardRadioButton.setText(getString(R.string.hard_level_text) + " " + pool.getHardNumber());
//    mediumRadioButton.setText(getString(R.string.medium_level_text) + " " + pool.getMediumNumber());
//    easyRadioButton.setText(getString(R.string.easy_level_text) + " " + pool.getEasyNumber());
//
//    wordsCounter.setText(getString(R.string.word_counter_text) + pool.getWordsNumber());

  }
}

