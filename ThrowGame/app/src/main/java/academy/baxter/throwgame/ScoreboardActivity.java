package academy.baxter.throwgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_activity);
        // Getting Scores
        Intent intent = getIntent();
        int new_score = intent.getExtras().getInt("score");
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int old_score = prefs.getInt("key", 0); //0 is the default value
        if (new_score > old_score) {
            // Saving the new Highscore
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", new_score);
            editor.commit();
        }
        // Updating UI Values
        TextView newScoreTextView = (TextView) findViewById(R.id.newScoreTextView);
        newScoreTextView.setText(Integer.toString(new_score));
        TextView oldScoreTextView = (TextView) findViewById(R.id.oldScoreTextView);
        oldScoreTextView.setText(Integer.toString(old_score));
    }

    public void switchToCountdown(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
    }
}
