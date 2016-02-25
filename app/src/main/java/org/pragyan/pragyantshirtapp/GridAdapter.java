package org.pragyan.pragyantshirtapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridAdapter extends ArrayAdapter<String> {


    private int eachHeight = Utilities.screenHeight / 4;
    private int lengthOfDataSet;
    private int offset;

    public GridAdapter(Context context, String[] objects, int offset) {
        super(context, R.layout.grid_layout, objects);
        lengthOfDataSet=objects.length;
        this.offset = offset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View v = inflater.inflate(R.layout.grid_layout, parent, false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, eachHeight));
        TextView textView = (TextView) v.findViewById(R.id.gridText);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.gridLinearLayout);
/*
        Random random = new Random();
        int n = random.nextInt(Utilities.strcolorsEvents.length);
        layout.setBackgroundColor(Color.parseColor(Utilities.strcolorsEvents[n]));
*/
        String color = getColor(position);
        layout.setBackgroundColor(Color.parseColor(color));
        String string = getItem(position);
        textView.setText(string);
        return v;
    }

    private String getColor(int position)
    {
        if(position==0) return Utilities.strcolorsEvents[(9+offset) % Utilities.strcolorsEvents.length];
        else if(position%2!=0) return Utilities.strcolorsEvents[(9+offset + position/2+1) % Utilities.strcolorsEvents.length];
        else return Utilities.strcolorsEvents[(9+offset + lengthOfDataSet - position/2) % Utilities.strcolorsEvents.length];
    }
}
