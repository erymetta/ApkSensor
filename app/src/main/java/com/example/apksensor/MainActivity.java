package com.example.apksensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximitySensor == null){
            Toast.makeText(this, "Proximity Sensor is NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            finish();
        }

        proximitySensorListner =new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
               if(sensorEvent.values[0]<proximitySensor.getMaximumRange()){
                   getWindow().getDecorView().setBackgroundColor(Color.WHITE);
               }else{
                   getWindow().getDecorView().setBackgroundColor(Color.RED);
               }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(proximitySensorListner, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();

        sensorManager.unregisterListener(proximitySensorListner);
    }
}