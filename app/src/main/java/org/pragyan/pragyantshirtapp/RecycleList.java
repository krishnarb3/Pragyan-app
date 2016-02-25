package org.pragyan.pragyantshirtapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.support.v7.widget.RecyclerView;

public class RecycleList extends RecyclerView.Adapter<RecycleList.CustomViewHolder> {
    public final Context context;
    public final String[][] present;

    public final int[][] time;

    public final String[] Number;

    Calendar timenow=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));


    int n=3,a,b,c,d;

    public RecycleList(Context context, String[][] present, int[][] time, int o,String[] Number) {

        this.context = context;
        this.present = present;
        this.time=time;
        this.Number=Number;

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView Event,Time,Location,Cate,text;
        public LinearLayout llglow;
        public RelativeLayout lay;

        public CustomViewHolder(View view) {
            super(view);

            this.Event = (TextView) view.findViewById(R.id.Event);
            this.Time = (TextView) view.findViewById(R.id.Time);
            this.Location=(TextView) view.findViewById(R.id.Location);
            this.Cate = (TextView) view.findViewById(R.id.Cate);
            this.lay=(RelativeLayout)itemView.findViewById(R.id.singlelistlayout);
            this.llglow = (LinearLayout) view.findViewById(R.id.glow);

        }
    }

    //function to remove underscores from the strings
    public String propergram(String word){


        // word.toLowerCase();
        String[] tempstr =word.split("_");
        String tempstr2;

        for(int i=0;i<tempstr.length;i++){
            if((tempstr[i].charAt(0))>='A'&&(tempstr[i].charAt(0))<='Z') {
                tempstr2 = (String.valueOf(tempstr[i].charAt(0)));
            }
            else {
                tempstr2 = (String.valueOf(tempstr[i].charAt(0))).toUpperCase();
            }
            tempstr[i] = tempstr2.concat(tempstr[i].substring(1));
            if (i != 0) {
                word=word.concat(" ").concat(tempstr[i]);
            } else {
                word = tempstr[i];
            }

        }
        return word;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        int position=i;
        String[] temp=present[position];

        Calendar time5 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
        time5.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), time[position][0], time[position][1], time[position][2]);

        Calendar time6 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
        time6.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), time[position][3], time[position][4], time[position][5]);

        customViewHolder.Event.setText(propergram(temp[0]));
        customViewHolder.Location.setText(propergram(temp[1]));
        customViewHolder.Cate.setText(propergram(temp[2]));
        customViewHolder.llglow.setVisibility(View.GONE);
        //calculates time left for begining an event
        if (time5.after(timenow)) {

            a =((int)((time5.getTimeInMillis() - timenow.getTimeInMillis())/3600000));
            b =((int)((time5.getTimeInMillis() - timenow.getTimeInMillis())/60000))%60;
            if(a!=0) {
                if(b==0)
                customViewHolder.Time.setText("begins in " + a + " hours ");
                else
                    customViewHolder.Time.setText("begins in " + a + " hours " + b + " mins ");
            }
            else
                customViewHolder.Time.setText("begins in " + b + " mins ");
        }
        //calculates time left for end of an ongoing event
        else if (time6.before(timenow)) {

            customViewHolder.Time.setText("ends in 0 hours 0 mins ");
        }
        else {

            c = ((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/3600000));
            d =((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/60000))%60;
            if(c!=0) {
                if (d == 0)
                    customViewHolder.Time.setText("ends in " + c + " hours ");
                else
                    customViewHolder.Time.setText("ends in " + c + " hours " + d + " mins ");
            }
            else
                customViewHolder.Time.setText("ends in " + d + " mins ");
            customViewHolder.llglow.setVisibility(View.VISIBLE);
            customViewHolder.llglow.setBackgroundColor(Color.parseColor("#00C853"));
        }
    }

    @Override
    public int getItemCount() {
        return (null != Number ? Number.length : 0);
    }
}