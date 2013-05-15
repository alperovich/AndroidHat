package com.alperovichsimon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.alperovichsimon.gamemodel.Player;
import com.alperovichsimon.gamemodel.Team;

import java.util.List;

public class TeamListAdapter extends ArrayAdapter<Team> {

  private final Team[] teams;
  private final int layoutResourceId;

  public TeamListAdapter(Context context, int layoutResourceId, Team[] teams) {
    super(context, layoutResourceId, teams);
    this.layoutResourceId = layoutResourceId;
    this.teams = teams;
  }

  public int getLayoutResourceId() {
    return layoutResourceId;
  }

  private static class TeamViewHolder {
    private TextView firstPlayer;
    private TextView secondPlayer;

    private TextView getFirstPlayer() {
      return firstPlayer;
    }

    private void setFirstPlayer(TextView firstPlayer) {
      this.firstPlayer = firstPlayer;
    }

    private TextView getSecondPlayer() {
      return secondPlayer;
    }

    private void setSecondPlayer(TextView secondPlayer) {
      this.secondPlayer = secondPlayer;
    }
  }


  /* speed up by using static @TeamViewHolder class */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    //TODO: more than two players in a team
    View row = convertView;
    TeamViewHolder holder = null;
    if (row == null) {
      LayoutInflater inflater;
      inflater = LayoutInflater.from(this.getContext());
      row = inflater.inflate(this.getLayoutResourceId(), parent, false);
      TextView firstPlayer = (TextView) row.findViewById(R.id.first_player);
      TextView secondPlayer = (TextView) row.findViewById(R.id.second_player);
      holder = new TeamViewHolder();
      holder.setFirstPlayer(firstPlayer);
      holder.setSecondPlayer(secondPlayer);
      row.setTag(holder);
    } else {
      holder = (TeamViewHolder) row.getTag();
    }
    Team curTeam = teams[position];
    assert (curTeam.getPlayers().size() > 1);
    List<Player> teamPlayers = curTeam.getPlayers();
    holder.getFirstPlayer().setText(teamPlayers.get(0).getName());
    holder.getSecondPlayer().setText(teamPlayers.get(1).getName());
    if (position % 2 == 0)
      row.setBackgroundColor(Color.GRAY);
    else
      row.setBackgroundColor(Color.rgb(255, 150, 150));

    return row;
  }
}
