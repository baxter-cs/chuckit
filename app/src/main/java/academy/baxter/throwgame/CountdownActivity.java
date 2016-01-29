package academy.baxter.throwgame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class CountdownActivity extends AppCompatActivity {

    private Handler aHandler = new Handler();
    private Handler bHandler = new Handler();
    private Handler cHandler = new Handler();
    private Handler dHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.countdown_activity);
        aHandler.postDelayed(new Runnable() {
            public void run() {
                TextView countdownTextView=(TextView)findViewById(R.id.countdownTextView);
                countdownTextView.setText("2");
                bHandler.postDelayed(new Runnable() {
                    public void run() {
                        TextView countdownTextView = (TextView) findViewById(R.id.countdownTextView);
                        countdownTextView.setText("1");
                        cHandler.postDelayed(new Runnable() {
                            public void run() {
                                TextView countdownTextView = (TextView) findViewById(R.id.countdownTextView);
                                countdownTextView.setTextSize(60);
                                countdownTextView.setText("THROW!");
                                dHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        switchToSensor();
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }, 1000);
    }

    public void switchToSensor(){
        Intent intent = new Intent(CountdownActivity.this, SensorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
