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
    private final static String NOT_SUPPORTED_MESSAGE = "Sorry, Ambient Temperature sensor not available for this device!";

    static {
        System.loadLibrary("native-lib"); // to load our c++ code by referring to CMakeList
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.cardrecycle);

        // list of 5 elements with day, temperature and scale(Celsius: C / Farenhiet : F)
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
        //System.out.println(hello()); testing JNI
    }

    public void setWeatherList(ArrayList<CardContent> list){
        //update the list when some value changes i.e. scale for this sample
        mAdapter=new CardAdapter(list);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void changeTempScale(View view){
        String celsius = getResources().getString(R.string.celsiusbutton);
        String fahrenheit = getResources().getString(R.string.fahrenheitbutton);

        //change button text according to cuurent scale of temperature
        if(changeButton.getText().equals(fahrenheit)){
            changeButton.setText(celsius);
        }
        else if(changeButton.getText().equals(celsius))
            changeButton.setText(fahrenheit);

        ArrayList<CardContent> res = changeScale(cardContent);
        /*for(CardContent c:res){
            System.out.println(c.getDay()+" "+c.getTemperature()+" "+c.getScale());
        }
        //this shows the actual float values for all the temperatures
        */
        setWeatherList(res);
    }
    public void init(){
        //initialize the ambient tempertaure sensor and start the listener
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
        // release the sensor listener on exit from the app
    }

    //sensor event listener to continuously listen to the changes is temperature recorded by the sensor
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

        String scale = "";
        float tempList[]= new float[list.size()], res[];
        int i=0;

        for(CardContent c:list){

            if(c.getScale().equals("C")){ // C -> celsius
                c.setScale("F"); // List is currently in Celsius and we want to change it to Fahrenheit, we change the scale here itself
                if(scale.isEmpty())
                    scale=c.getScale();
                tempList[i]=Float.parseFloat(c.getTemperature());
            }
            else if(c.getScale().equals("F")){ // F -> fahrenheit
                c.setScale("C");
                if(scale.isEmpty())
                    scale=c.getScale();
                tempList[i]=Float.parseFloat(c.getTemperature());
            }
            i++;
        }
        if(scale.equals("F"))
            res = convertToFahrenheit(tempList);
        else
            res = convertToCelsius(tempList);

        int reslen = res.length;

        for(int j=0; j<reslen; j++){
            list.get(j).setTemperature(Float.toString(res[j])); // set the temperture for the particular day and order of the elements is fixed
        }

        return list;
    }


    public native float[] convertToFahrenheit(float[] f);
    public native float[] convertToCelsius(float[] f);

    /*
    //initial functions, trying out JNI and calling changeToFahrenheit and celsius on single value
    public  native String hello();
    public native float changeToFahrenheit(float n);
    public native float changeToCelsius(float n);*/

}
