package academy.baxter.throwgame;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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



public class Menu extends AppCompatActivity {


    public void showAlert(View view) {
        AlertDialog.Builder creditAlert = new AlertDialog.Builder(this);
        creditAlert.setMessage("2016 Baxter-Academy Object Orianted Programming Class")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        creditAlert.show();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menupopup);
        Switch switch1 = (Switch)findViewById(R.id.switch1);
        Switch switch2 = (Switch)findViewById(R.id.switch2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .5), (int) (height * .6));
        if (getSharedPreferences("AdDisplay", Context.MODE_PRIVATE).getBoolean("ads", true)){
            switch1.setChecked(true);}
        else{switch1.setChecked(false);}
        if (getSharedPreferences("Sounds", Context.MODE_PRIVATE).getBoolean("beeps",true)){
            switch2.setChecked(true);
        }else{switch2.setChecked(false);}
    }



    public void toggle(View view){

        Switch switch1 = (Switch)findViewById(R.id.switch1);

        SharedPreferences preferences = this.getSharedPreferences("AdDisplay", Context.MODE_PRIVATE);
        if (switch1.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("ads", true);
            editor.commit();

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
            editor.commit();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("beeps",false);
            editor.commit();
        }
    }

    public void SwitchToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}