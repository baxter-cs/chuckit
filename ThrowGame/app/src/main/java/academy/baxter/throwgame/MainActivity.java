package academy.baxter.throwgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void switchToCountdown(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
