package com.addb.weather;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CardContent> cardContent=new ArrayList<>();
    private Button changeButton;

    private TextView temperaturelabel;
    private SensorManager mSensorManager;
    private Sensor mTemperature;
    private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.cardrecycle);

        CardContent c1=new CardContent("Monday","25","C");
        CardContent c2=new CardContent("Tuesday","26","C");
        CardContent c3=new CardContent("Wednesday","27","C");
        CardContent c4=new CardContent("Thursday","28","C");
        CardContent c5=new CardContent("Friday","24","C");

        cardContent.add(c1);
        cardContent.add(c2);
        cardContent.add(c3);
        cardContent.add(c4);
        cardContent.add(c5);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardAdapter(cardContent);
        mRecyclerView.setAdapter(mAdapter);
        //setWeatherList(cardContent);
        changeButton = (Button)findViewById(R.id.changebutton);

        temperaturelabel = (TextView)findViewById(R.id.ambient);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        init();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            mTemperature= mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // requires API level 14.
        }
        if (mTemperature == null) {
            temperaturelabel.setText(NOT_SUPPORTED_MESSAGE);
        }
        System.out.println(hello());
        //System.out.println(changeScale(4)+" -----------");
    }

    public void setWeatherList(ArrayList<CardContent> list){


        mAdapter=new CardAdapter(list);
        // fire the event
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void changeTempScale(View view){
        if(changeButton.getText().equals("To Farenheit")){
            changeButton.setText("To Celsius");
        }
        else if(changeButton.getText().equals("To Celsius"))
            changeButton.setText("To Farenheit");
        ArrayList<CardContent> res = changeScale(cardContent);
        for(CardContent c:res){
            System.out.println(c.getDay()+" "+c.getTemperature()+" "+c.getScale());
        }
        setWeatherList(res);
    }
    public void init(){
        mSensorManager.registerListener(_SensorEventListener, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(_SensorEventListener, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(_SensorEventListener);
    }
    SensorEventListener _SensorEventListener=   new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float ambient_temperature = event.values[0];
            temperaturelabel.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public ArrayList<CardContent> changeScale(ArrayList<CardContent> list){
        ArrayList<CardContent> result = new ArrayList<CardContent>();
        for(CardContent c:list){
            if(c.getScale().equals("C")){
                float far=changeToFarenheit(Float.parseFloat(c.getTemperature()));
                c.setScale("F");
                c.setTemperature(Float.toString(far));
            }
            else if(c.getScale().equals("F")){
                float cel=changeToCelsius(Float.parseFloat(c.getTemperature()));
                c.setScale("C");
                c.setTemperature(Float.toString(cel));
            }
            result.add(c);
        }

        return result;
    }

    public  native String hello();
    public native float changeToFarenheit(float n);
    public native float changeToCelsius(float n);

}
