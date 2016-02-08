package academy.baxter.throwgame;


import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainactivity);
        new SimpleEula(this).show();


        if (getSharedPreferences("AdDisplay", Context.MODE_PRIVATE).getBoolean("ads", true)){
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

    }

    public void openMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void switchToCountdown(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
    }
}
