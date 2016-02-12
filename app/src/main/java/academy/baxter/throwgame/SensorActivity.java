package academy.baxter.throwgame;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sensor_activity);
        if (getSharedPreferences("Sounds", Context.MODE_PRIVATE).getBoolean("beeps",true)){
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hitmarker);
        mp.start();}

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = Math.abs(sensorEvent.values[0]) * Math.abs(sensorEvent.values[0]);
            float y = (Math.abs(sensorEvent.values[1]) - 9) * (Math.abs(sensorEvent.values[1]) - 9);
            float z = Math.abs(sensorEvent.values[2]) * Math.abs(sensorEvent.values[2]);
            double foobarmitzfah = Math.sqrt(x + y + z);
            int currentSpeed = (int)foobarmitzfah;
            if (currentSpeed > 16) {
                total_speed = total_speed + currentSpeed;
            }
            TextView speedTextView = (TextView) findViewById(R.id.speedTextView);
            speedTextView.setText(Integer.toString(total_speed));

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 5000) {
                switchToScoreboard();
            }

        }
    }

    public void switchToScoreboard(){
        Intent intent = new Intent(SensorActivity.this, ScoreboardActivity.class);
        intent.putExtra("score", total_speed);
        startActivity(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private int total_speed = 0;
    private long lastUpdate = System.currentTimeMillis();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
