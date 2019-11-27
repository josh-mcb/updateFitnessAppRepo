package com.example.androidgrouptask;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class listAdapterforProgress extends ArrayAdapter<routeTimes> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    private static class ViewHolder {
        TextView routeName;
        TextView time;
    }

    public listAdapterforProgress(Context context, int resource, ArrayList<routeTimes> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String routeName = getItem(position).getRouteName();
        String time = getItem(position).getTime();
        //Create the person object with the information

        routeTimes person = new routeTimes(routeName,time);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.routeName = convertView.findViewById(R.id.textView1);
            holder.time = convertView.findViewById(R.id.textView2);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.routeName.setText(person.getRouteName());
        holder.time.setText(person.getTime());

        return convertView;
    }



}
