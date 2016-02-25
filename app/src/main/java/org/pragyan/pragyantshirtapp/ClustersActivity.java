package org.pragyan.pragyantshirtapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


public class ClustersActivity extends Activity {
    EventsAdapter eventsAdapter;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        eventsAdapter = new EventsAdapter(this);

        GridView grid = (GridView) findViewById(R.id.clustersGrid);
        ArrayList<String> clusters = new ArrayList<>();
        clusters = eventsAdapter.getCluster();
        String[] clusterArray = new String[clusters.size()];
        clusterArray = clusters.toArray(clusterArray);
        GridAdapter adapter = new GridAdapter(this,clusterArray, Utilities.offset);
        grid.setAdapter(adapter);
        Utilities.offset+=clusters.size();
        grid.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cluster = (String)parent.getItemAtPosition(position);
                        Intent i = new Intent(ClustersActivity.this,EventsActivity.class);
                        i.putExtra("cluster",cluster);
                        startActivity(i);
                    }
                }
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
