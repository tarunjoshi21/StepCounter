package com.stepcounter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private TextView mStepCounterTextView;
    boolean isActivityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)) {
            Log.i(TAG, "This phone supports FEATURE_SENSOR_STEP_COUNTER sensor");

            mStepCounterTextView = (TextView) findViewById(R.id.step_counter);
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        } else {
            Toast.makeText(this, "This phone does not supports FEATURE_SENSOR_STEP_COUNTER sensor", Toast.LENGTH_SHORT).show();
        }
    }



    protected void onResume() {
        super.onResume();
        isActivityRunning = true;
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            mSensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
        if (detectSensor != null) {
            mSensorManager.registerListener(this, detectSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    protected void onPause() {
        super.onPause();
        isActivityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

   /* private void registerListeners(int sensorType) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(sensorType);

    }*/

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_STEP_COUNTER:
               /* //Since it will return the total number since we registered we need to subtract the initial amount
                //for the current steps since we opened app
                Log.i(TAG, "Initial Counter Value: "+counterSteps);
                if (counterSteps < 1) {
                    // initial value
                    counterSteps = (int)sensorEvent.values[0];
                }

                Log.i(TAG, "sensorEvent.values[0]: "+sensorEvent.values[0]);

                // Calculate steps taken based on first counter value received.
                stepCounter = (int)sensorEvent.values[0] - counterSteps;

                ((TextView)findViewById(R.id.step_counter)).setText("Steps using TYPE_STEP_COUNTER: "+stepCounter);*/
                if (isActivityRunning) {
                    mStepCounterTextView.setText(String.valueOf(sensorEvent.values[0]));
                }
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
