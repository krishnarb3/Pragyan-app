package org.pragyan.pragyantshirtapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class EventsActivity extends Activity {
    EventsAdapter eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Bundle b = getIntent().getExtras();
        String cluster = b.getString("cluster");
        eventsAdapter = new EventsAdapter(this);
        GridView grid = (GridView) findViewById(R.id.clustersGrid);
        ArrayList<String> events = new ArrayList<>();
        events = eventsAdapter.getEventnamesOfCluster(cluster);
        String[] eventsArray = new String[events.size()];
        eventsArray = events.toArray(eventsArray);
        GridAdapter adapter = new GridAdapter(this,eventsArray, Utilities.offset);
        grid.setAdapter(adapter);
        Utilities.offset = Utilities.offset + events.size();
        grid.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String eventName = (String) parent.getItemAtPosition(position);
                        Intent i = new Intent(EventsActivity.this,EventDetailActivity.class);
                        i.putExtra("eventName",eventName);
                        startActivity(i);
                    }
                }
        );
    }

}
