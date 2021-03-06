package org.pragyan.pragyantshirtapp;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.TimeZone;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class UpcomingActivity extends AppCompatActivity {


    String[] Number;
    String[][] present;
    String[][] tempeve;
    int[][] prtime;
    int[][] temptime;
    int[] id;
    String[] evstarttime; //array of start times off all events
    String[] evendtime;  //array of end times off all events
    String[] evdate;   //array of dates off all events
    String[] evlastupdate;    //array of last updates off all events

    //flag variables
    int no,t,ch=-1,catech=-1;
    int timelimit=1;

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    String noentrytest="No ongoing/upcoming events.\nWhy not visit the FOODSTALLS instead?";  //default text for when no event in list

    //new data for dynamic spinnerviews
    String[] venues={"barn"};
    String[] cates={"dramatics"};
    int co1=1,co2=1;
    Spinner spinner ;
    Spinner spinnertime;
    Spinner spinnercate;
    ArrayAdapter<String> spadapter;
    ArrayAdapter<String> caadapter;




    public void onSaveInstanceState(Bundle savedInstanceState) {
        //to retain spinner state
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("spinner1", spinner.getSelectedItemPosition());
        savedInstanceState.putInt("spinner2", spinnertime.getSelectedItemPosition());
        savedInstanceState.putInt("spinner3", spinnercate.getSelectedItemPosition());
    }



    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);



        if (savedInstanceState != null) {
            //updates flag depending on current state of the spinners
            ch = savedInstanceState.getInt("spinner1") - 1;
            timelimit = savedInstanceState.getInt("spinner2") + 1;
            catech = savedInstanceState.getInt("spinner3") - 1;
        }


    }




    protected void sort(String[][] tevents,  int[][] ttime,  int o){    //function to extract needed elements from database

        Calendar timenow=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
        String[] temp;
        int[] tem;
        int chcheck=0,catechcheck=0;

        prtime=new int[o][6];
        String[][] store=new String[o][3];

        for(int i=0;i<o;i++){  //sorts list of event according to event start time, uses bubble sort

            for(int j=i;j<o-1-i;j++){
                Calendar time1=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
                Calendar time2=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
                time1.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), ttime[j][0], ttime[j][1], ttime[j][2]);
                time2.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), ttime[j + 1][0], ttime[j + 1][1], ttime[j + 1][2]);


                if(time1.after(time2)){
                    temp=tevents[j];
                    tevents[j]=tevents[j+1];
                    tevents[j+1]=temp;

                    tem=ttime[j];
                    ttime[j]=ttime[j+1];
                    ttime[j+1]=tem;


                }
            }
        }


        Calendar time3=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));   //current time plus 1/2 hours depending on time limit selected in spinner list
        time3.set(Calendar.YEAR,timenow.get(Calendar.YEAR));
        time3.set(Calendar.MONTH,timenow.get(Calendar.MONTH));
        time3.set(Calendar.DATE,timenow.get(Calendar.DATE));
        time3.set(Calendar.HOUR_OF_DAY,timenow.get(Calendar.HOUR_OF_DAY)+timelimit);
        time3.set(Calendar.MINUTE,timenow.get(Calendar.MINUTE));


        //checks if time 3 extends over to next day i.e. it crosses midnight
        if(time3.get(Calendar.HOUR_OF_DAY)>23){
            time3.set(Calendar.HOUR_OF_DAY,((timenow.get(Calendar.HOUR_OF_DAY))-24));
            time3.set(Calendar.DATE,(timenow.get(Calendar.DATE)+1));
        }


        t=0;


        //to select events based on category selected in spinner list
        for(int k=0;k<o;k++) {

            Calendar time4 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time4.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), ttime[k][0], ttime[k][1], ttime[k][2]);

            Calendar time5 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time5.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), ttime[k][3], ttime[k][4], ttime[k][5]);

            if ((time4.before(time3)) && timenow.before(time5)) {


                catechcheck=0;
                chcheck=0;


                if(catech==-1){
                    catechcheck=1;
                }


                else if((tevents[k][2].equalsIgnoreCase(cates[catech]))){
                    catechcheck=1;
                }



                if(ch==-1){
                    chcheck=1;
                }

                else if((tevents[k][1].equalsIgnoreCase(venues[ch]))){
                    chcheck=1;
                }



                if(chcheck==1&&catechcheck==1) {
                    store[t] = tevents[k];
                    prtime[t] = ttime[k];
                    t++;
                }
            }
        }



        present=new String[t][3];
        Number = new String [t];

        //final sorted event list
        for (int q = 0; q < t; q++) {

            present[q] = store[q];


        }
    }

    //gets events stored in database
    public void parseevents(){

                            EventsAdapter Adapter= new EventsAdapter(getApplicationContext());

                            ArrayList<EventInfo> E=Adapter.getAllEvents();

                            no=E.size();
                            tempeve=new String[no][3];
                            temptime=new int[no][6];
                            id=new int[no];

                            evstarttime=new String[no];
                            evendtime=new String[no];
                            evdate=new String[no];
                            evlastupdate=new String[no];
                            String[] temp1=new String[no];
                            String[] temp2=new String[no];
                            co1=0;
                            co2=0;

                            //event information entered one by one
                            for (int i = 0; i < no; i++) {

                                tempeve[i][0] = E.get(i).name;
                                evstarttime[i] =E.get(i).start_time;
                                evendtime[i] = E.get(i).end_time;
                                tempeve[i][1] = E.get(i).venue;
                                tempeve[i][2] =E.get(i).cluster;
                                evdate[i] = E.get(i).date;
                                id[i] =  E.get(i).id;
                                evlastupdate[i]=E.get(i).last_update_time;

                                temptime[i][0] = (Integer.parseInt(evdate[i].substring(8, 10)));
                                temptime[i][1] = (Integer.parseInt(evstarttime[i].substring(0, 2)));
                                temptime[i][2] = (Integer.parseInt(evstarttime[i].substring(3, 5)));
                                temptime[i][4] = (Integer.parseInt(evendtime[i].substring(0, 2))) ;
                                temptime[i][5] = (Integer.parseInt(evendtime[i].substring(3, 5)));
                                if (temptime[i][1] < temptime[i][4]) {
                                    temptime[i][3] = temptime[i][0] ;
                                }
                                else if(temptime[i][2]<temptime[i][5]){
                                    temptime[i][3] = temptime[i][0] ;
                                }
                                else{
                                    temptime[i][3] = temptime[i][0]+1 ;
                                }

                                if(i==0){
                                    temp1[0]=tempeve[0][1];
                                    temp2[0]=tempeve[0][2];
                                    co1=1;
                                    co2=1;
                                }

                                int ca=0;
                                for(int j=0;j<co1;j++) {
                                    if ((tempeve[i][1].equalsIgnoreCase(temp1[j]))) {
                                        ca++;
                                        break;
                                    }

                                }
                                if (ca == 0) {
                                    temp1[co1] = tempeve[i][1];
                                    co1++;
                                }
                                ca=0;

                                for(int j=0;j<co2;j++) {
                                    if ((tempeve[i][2].equalsIgnoreCase(temp2[j]))) {
                                        ca++;
                                        break;
                                    }

                                }
                                if (ca == 0) {
                                    temp2[co2] = tempeve[i][2];
                                    co2++;
                                }
                            }
                            venues=new String[co1];
                            cates=new String[co2];
                            for(int j=0;j<co1;j++){
                                venues[j]=temp1[j];
                            }
                            for(int j=0;j<co2;j++){
                                cates[j]=temp2[j];
                            }


                            handlerecycle();
                            optsel();
                            optsel();
    }


    //updating list when spinner option is changed
    public void optsel(){
        if(tempeve!=null) {


            sort(tempeve, temptime, no);
        }
        RecycleList adapter = new
                RecycleList(UpcomingActivity.this, present,prtime,t,Number);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclelist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(UpcomingActivity.this));
        mRecyclerView.setAdapter(adapter);
        TextView texx=(TextView) findViewById(R.id.noevent);
        if(t==0){
            texx.setText(noentrytest);

        }
        else{
            texx.setText(null);
        }


    }

    //to deal with flag updations when spinner list changes
    public void handlerecycle(){
        String[] items=new String[co1+1];
        String[] tempa =new String[co1+1];
        items[0]="All";
        tempa[0]="All";
        int c=1;
        if(venues!=null) {

            for (int i = 0; i < co1; i++) {
                items[i + 1] = propergram(venues[i]);
            }
        }

        //spinner for venues
        spinner = (Spinner) findViewById(R.id.recyclespinner);
        spadapter = new ArrayAdapter<String>(
                this, R.layout.spinnerstyle, items);
        spinner.setAdapter(spadapter);
        spinner.setSelection(ch + 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ch = position - 1;
                optsel();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                optsel();

            }


        });

        //spinner for time limit
        spinnertime = (Spinner) findViewById(R.id.recyclespinnertime);
        String[] itemstime = new String[] { "1 hour", "2 hours"};
        ArrayAdapter<String> tiadapter = new ArrayAdapter<String>(
                this, R.layout.spinnerstyle, itemstime);
        spinnertime.setAdapter(tiadapter);
        spinnertime.setSelection(timelimit - 1);
        spinnertime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch (position) {

                    case 0:
                        timelimit = 1;
                        break;

                    case 1:
                        timelimit = 2;
                        break;
                }
                optsel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


                optsel();

            }
        });

        //spinner for category
        spinnercate = (Spinner) findViewById(R.id.recyclespinnercate);
        String[] itemscate = new String[co2+1];
        itemscate[0]="All";
        if(cates!=null) {
            for (int i = 0; i < co2; i++) {
                itemscate[i + 1] = propergram(cates[i]);
            }
        }
        caadapter = new ArrayAdapter<String>(
                this, R.layout.spinnerstyle, itemscate);
        spinnercate.setAdapter(caadapter);
        spinnercate.setSelection(catech + 1);
        spinnercate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                catech = position - 1;
                optsel();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


                optsel();

            }
        });

    }


    //to format the text to remove underscores and to make some uppercase
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.faux_main);

        setTitle("Upcoming Events Schedule");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pragyan_dark_grey)));

        parseevents();


    }

}