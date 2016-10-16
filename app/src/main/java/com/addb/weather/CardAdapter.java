package com.addb.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arjun on 10/15/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ArrayList<CardContent> data;
    static Context context;
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {

        TextView day = (TextView)holder.view.findViewById(R.id.day);
        TextView temperature = (TextView)holder.view.findViewById(R.id.temperature);
        //TextView scale = (TextView)holder.view.findViewById(R.id.scale);
        String scale="";
        day.setText(data.get(position).getDay());
        String tempvalue= data.get(position).getTemperature();
        int indexOfDecimal = tempvalue.indexOf('.');
        if(indexOfDecimal!=-1)
        tempvalue = tempvalue.substring(0, indexOfDecimal);

        if(data.get(position).getScale().equals("C")){
            scale =context.getResources().getString(R.string.celsius);
        }
        else if(data.get(position).getScale().equals("F")){
            scale= context.getResources().getString(R.string.farenheit);
        }
        temperature.setText(tempvalue+scale);
        //if(data.get(position).getScale() == "c")


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }

    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(ArrayList<CardContent> data) {
        this.data = data;
    }

    public ArrayList<CardContent> getData(){
        return data;
    }
}
