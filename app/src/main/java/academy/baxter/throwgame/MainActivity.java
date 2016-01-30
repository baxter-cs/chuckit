package academy.baxter.throwgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.io.File;



public class MainActivity extends AppCompatActivity {
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);
        new SimpleEula(this).show();
    }






    public void switchToCountdown(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }

    /*public void openMenu(View view) {
        Intent intent = new Intent(this,Menu.class);
        startActivity(intent);
    }
*/
    @Override
    public void onBackPressed() {
    }
}
