package com.alperovichsimon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.alperovichsimon.gamemodel.Player;
import com.alperovichsimon.gamemodel.Team;
import com.alperovichsimon.gamemodel.TeamPool;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MustDie
 * Date: 09.05.13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class StatsActivity extends Activity{

    private GridView statsView;
    private List<Team> teams;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        teams = TeamPool.getInstance().getPool();
        Collections.sort(teams,new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                return team2.guessedWordsCount() - team1.guessedWordsCount();
            }
        });

        statsView = (GridView) findViewById(R.id.stats_grid_view);
        statsView.setAdapter(new ImageAdapter(this));
        statsView.setNumColumns(2);


    }


    public class ImageAdapter extends BaseAdapter {
        private Context myContext;

        public ImageAdapter(Context context) {
            myContext = context;
        }

        public int getCount() {
            return TeamPool.getInstance().getTeamsNumber() * 2;

        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;


            if (convertView == null) {
                textView = new TextView(myContext);
                int teamsNumber = TeamPool.getInstance().getTeamsNumber();
                if (position % 2 == 0) {
                    Team currentTeam = teams.get(position / 2);
                    textView.setText(getTeamPresentation(currentTeam));
                } else {
                    Team currentTeam = teams.get((position - 1) / 2);
                    textView.setText(String.valueOf(currentTeam.guessedWordsCount()));
                }
            } else {
                textView = (TextView) convertView;
            }


            return textView;
        }

        private StringBuilder getTeamPresentation(Team currentTeam) {
            List<Player> players = currentTeam.getPlayers();
            StringBuilder teamPresent = new StringBuilder(players.get(0).getName());
            for (int i = 1; i < players.size(); ++i) {
                teamPresent.append(" и ");
                teamPresent.append(players.get(i));

            }
            return teamPresent;
        }


    }
}
