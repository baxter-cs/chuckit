package academy.baxter.throwgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameUtils;


public class ScoreboardActivity extends AppCompatActivity {


    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.scoreboard_activity);


        if (getSharedPreferences("AdDisplay", Context.MODE_PRIVATE).getBoolean("ads", true)){
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }



        // Getting Scores
        Intent intent = getIntent();
        int new_score = intent.getExtras().getInt("score");
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int old_score = prefs.getInt("HighScore", 0); //0 is the default value
        if (new_score > old_score) {
            // Saving the new Highscore
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HighScore", new_score);
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
    public void switchToMaineActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
