package com.addb.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CardContent> cardContent=new ArrayList<>();

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
    }
}
