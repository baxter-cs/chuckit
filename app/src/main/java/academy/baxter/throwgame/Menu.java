package academy.baxter.throwgame;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


public class Menu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.menupopup);

        Switch switch1 = (Switch)findViewById(R.id.switch1);
        Switch switch2 = (Switch)findViewById(R.id.switch2);
        Switch switch3 = (Switch)findViewById(R.id.autosignin);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .75), (int) (height * .75));
        if (getSharedPreferences("AdDisplay", Context.MODE_PRIVATE).getBoolean("ads", true)){
            switch1.setChecked(true);}
        else{switch1.setChecked(false);}
        if (getSharedPreferences("Sounds", Context.MODE_PRIVATE).getBoolean("beeps",true)){
            switch2.setChecked(true);
        }else{switch2.setChecked(false);}
        if (getSharedPreferences("autoSignIn", Context.MODE_PRIVATE).getBoolean("auto", true)){
            switch3.setChecked(true);
        }else{
            switch3.setChecked(false);
        }
    }

    public void toggle(View view){
        Switch switch1 = (Switch)findViewById(R.id.switch1);
        SharedPreferences preferences = this.getSharedPreferences("AdDisplay", Context.MODE_PRIVATE);
        if (switch1.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("ads", true);
            editor.apply();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("ads", false);
            editor.commit();
        }
    }
    public void toggle2(View view){
        Switch switch2 = (Switch)findViewById(R.id.switch2);
        SharedPreferences preferences = this.getSharedPreferences("Sounds", Context.MODE_PRIVATE);
        if (switch2.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("beeps", true);
            editor.apply();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("beeps",false);
            editor.apply();
        }
    }
    public void toggle3(View view){
        Switch switch3 = (Switch)findViewById(R.id.autosignin);
        SharedPreferences preferences = this.getSharedPreferences("autoSignIn", Context.MODE_PRIVATE);
        if (switch3.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("auto", true);
            editor.apply();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("auto", false);
            editor.apply();
        }
    }

    public void SwitchToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openWebPage(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.AboutWebpage)));
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}