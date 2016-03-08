package academy.baxter.throwgame;

import com.google.android.gms.common.ConnectionResult;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.android.gms.games.Games;
import android.view.GestureDetector.*;
import android.widget.ImageView;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnGestureListener, OnDoubleTapListener {

    GoogleApiClient mGoogleApiClient;
    private static ImageView imageView;
    private GestureDetectorCompat gestureDetector;

    protected void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainactivity);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.chuckit);
        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .build();
        new SimpleEula(this).show();
        if (getSharedPreferences("AdDisplay", Context.MODE_PRIVATE).getBoolean("ads", true)){
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        boolean signin = getSharedPreferences("autoSignIn", Context.MODE_PRIVATE).getBoolean("auto", true);
        if (signin){
            mAutoStartSignInFlow = true;
        }else{
            mAutoStartSignInFlow = false;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;
    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }
        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
        else if (view.getId() == R.id.sign_out_button) {
            // sign out.
            mSignInClicked = false;
            Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            // show sign-in button, hide the sign-out button
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.leaderboards).setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // The player is signed in. Hide the sign-in button and allow the
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        findViewById(R.id.leaderboards).setVisibility(View.VISIBLE);
        SharedPreferences preferences = this.getSharedPreferences("Achievements", Context.MODE_PRIVATE);
        int unlocked = preferences.getInt("unlocked", 0);
        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_ID),getSharedPreferences("myPrefsKey",Context.MODE_PRIVATE).getInt("HighScore", 0));
        if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>0 && unlocked == 0){
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_hurrah_first_chuck));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("unlocked", 1);
            editor.commit();
        } if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>200 && unlocked == 1){
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_gaining_power));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("unlocked", 2);
            editor.commit();
        } if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>500 && unlocked == 2){
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_have_you_been_working_out));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("unlocked", 3);
            editor.commit();
        } if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>900 && unlocked == 3){
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_take_it_easy));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("unlocked", 4);
            editor.commit();
        } if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>1200 && unlocked == 4) {
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_slow_down_champ_we_get_it));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("unlocked", 5);
            editor.commit();
        } if(getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE).getInt("HighScore", 0)>5000 && unlocked == 5) {
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_its_official_your_hulk));
        }

        // player to proceed.
    }
    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }



    public void openMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void switchToCountdown(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }
    private static final int RC_UNUSED = 3001;

    public void LeaderboardStart(View view) {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.leaderboards_not_available)).show();
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), RC_UNUSED);
            }
        });
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    return;

    }
        @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;

    }
}