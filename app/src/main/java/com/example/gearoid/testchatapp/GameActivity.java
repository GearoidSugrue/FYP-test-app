package com.example.gearoid.testchatapp;

import android.app.DialogFragment;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.gearoid.testchatapp.character.EvilCharacter;
import com.example.gearoid.testchatapp.singletons.Player;


public class GameActivity extends ActionBarActivity implements TeamVoteFragment.TeamVoteDialogListener, QuestVoteFragment.QuestVoteDialogListener, SelectTeamFragment.TeamSelectDialogListener
                                                               , AssassinateFragment.AssassinateDialogListener {

    public GameBoardFragment gameBoardFrag;
    public GameLogicFunctions.Board currentBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

        setContentView(R.layout.activity_game);

        currentBoard = (GameLogicFunctions.Board) getIntent().getSerializableExtra("BOARD");
        initialiseFragments();
        initialiseButtons();
    }

    private void initialiseFragments() {
        gameBoardFrag = (GameBoardFragment) getFragmentManager()
                .findFragmentById(R.id.layout_gameBoardFragment);

//        Bundle args = new Bundle();
//        args.putSerializable("BOARD", currentBoard);
//        gameBoardFrag.setArguments(args);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialiseButtons(){
        Button teamVoteFrag = (Button) findViewById(R.id.button_teamVoteFrag);
        Button questVoteFrag = (Button) findViewById(R.id.button_questVoteFrag);
        Button teamSelectFrag = (Button) findViewById(R.id.button_teamSelectFrag);
        Button button_assassinateFrag = (Button) findViewById(R.id.button_assassinateFrag);

        teamVoteFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = TeamVoteFragment.newInstance(new int[]{0, 1, 3, 4}, 1 , 3); //TODO change to voteCount + QuestCount
                newFragment.show(getFragmentManager(), "teamdialog");
            }
        });

        questVoteFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = QuestVoteFragment.newInstance(new int[]{0, 2, 3}, Player.getInstance().character instanceof EvilCharacter, 1); //TODO change to questNum...
                newFragment.show(getFragmentManager(), "questdialog");
            }
        });

        teamSelectFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = SelectTeamFragment.newInstance(3, 2); //TODO change to questNum...
                newFragment.show(getFragmentManager(), "teamselectdialog");
            }
        });

        button_assassinateFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = AssassinateFragment.newInstance(); //TODO change to questNum...
                newFragment.show(getFragmentManager(), "assassinatedialog");
            }
        });

    }

    @Override
    public void onVoteSelected(boolean voteResult) {
        Log.d("GameActivity", "Vote Result received from team vote dialog: " + voteResult);

        //TODO Send vote result to server
    }

    @Override
    public void onQuestVoteSelected(boolean voteResult) {
        Log.d("GameActivity", "Vote Result received from quest vote dialog: " + voteResult);

        //TODO Send vote result to server

    }

    @Override
    public void onTeamSelected(int[] teamIndexes) {
        Log.d("GameActivity", "Team selected received from TeamSelect dialog.");
        //TODO Send player indexes to server result to server


    }

    @Override
    public void onAssassination(boolean isSuccess) {
        Log.d("GameActivity", "OnAssassinate received from Assassinate dialog. Result: " + isSuccess);
        //TODO Send player indexes to server result to server

    }
}
