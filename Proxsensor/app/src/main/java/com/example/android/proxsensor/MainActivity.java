package com.example.android.proxsensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.media.RingtoneManager.getDefaultUri;
import static android.media.RingtoneManager.getRingtone;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView T;
    SensorManager sm;
    Sensor ps;
    Ringtone dr = null;
    RingtoneManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T = (TextView) findViewById(R.id.tv);
        Uri tone = rm.getDefaultUri(RingtoneManager.TYPE_ALARM);
        dr = rm.getRingtone(this,tone);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ps = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float d = event.values[0];
        String text;

        if(d==0)
        {
            dr.play();
            text = "USER IS NEAR THE DEVICE";
        }

        else
        {
            dr.stop();
            text = "USER IS FAR FROM THE DEVICE";
        }

        T.setText(text);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        dr.stop();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sm.registerListener(this,ps,SensorManager.SENSOR_DELAY_NORMAL);
    }

}
