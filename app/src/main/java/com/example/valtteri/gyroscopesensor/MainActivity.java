package com.example.valtteri.gyroscopesensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor gyro;
    private int selectAxis = 2; // default axis is this
    private Button xbtn, zbtn, ybtn;
    private TextView helptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            Log.i("Sensor", "It has");
        }

        else {
            Log.i("Sensor", "It hasn't");
        }
        helptext = (TextView)findViewById(R.id.help);
        helptext.setText("Rotate your phone to left or right while screen is towards to you.");
        xbtn = (Button)findViewById(R.id.x_axis);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAxis = 0;
                helptext.setText("Turn screen towards or away from you.");
            }
        });

        ybtn = (Button)findViewById(R.id.y_axis);
        ybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAxis = 1;
                helptext.setText("Twist your phone to left or right.");
            }
        });
        zbtn = (Button)findViewById(R.id.z_axis);
        zbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAxis = 2;
                helptext.setText("Rotate your phone to left or right while screen is towards to you.");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sm != null) {
            sm.registerListener(this, gyro, sm.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }



    @Override
    public final void onSensorChanged(SensorEvent event) {
        //all axis values
        float axisX = event.values[0];
        float axisY = event.values[1];
        float axisZ = event.values[2];

        switch (selectAxis) {
            case 0:
                if(event.values[0] > 0.5f) { // towards you
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else if (event.values[0] < -0.5f) { // away from you
                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                }
                break;
            case 1:
                if(event.values[1] > 0.5f) { // towards you
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else if (event.values[1] < -0.5f) { // away from you
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                }
                break;
            case 2:
                if(event.values[2] > 0.5f) { // anticlockwise
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if(event.values[2] < -0.5f) { // clockwise
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                break;
            default:
                break;


        }





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}
