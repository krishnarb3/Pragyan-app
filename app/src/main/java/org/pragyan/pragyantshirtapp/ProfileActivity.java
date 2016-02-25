package org.pragyan.pragyantshirtapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends ActionBarActivity {

    ListView regEventsList;
    fTextView lt;
    ArrayAdapter<String> eventsArrayAdapter;
    Read_write_file file;
    ArrayList<String> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        file=new Read_write_file(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.background_floating_material_dark)));
        lt=(fTextView)findViewById(R.id.loadingtext);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'> Profile </font>"));
        fTextView welcome = (fTextView) findViewById(R.id.welcomeTextfull);
        fTextView p_id = (fTextView) findViewById(R.id.p_id);
        fTextView email = (fTextView) findViewById(R.id.email_id);
        welcome.setText("Welcome, "+Utilities.fullname+"!");
        p_id.setText("Pragyan ID: "+String.valueOf(Utilities.pid));
        email.setText("Email ID: "+Utilities.pragyan_mail);
        setData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //Logout
            SharedPreferences.Editor editor = Utilities.sp.edit();
            editor.putInt("status", 0);
            editor.putString("pragyan_mail", null);
            editor.putString("pragyan_pass", null);
            editor.putString("name", null);
            editor.putString("fullname", null);
            editor.putInt("pid", -1);
            Utilities.pragyan_mail = "";
            Utilities.pid = -1;
            Utilities.pragyan_pass = "";
            Utilities.name = null;
            Utilities.fullname = null;
            Utilities.status = 0;
            editor.apply();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            return true;
        }
        if (id==R.id.qr_code)
        {
            //Open QR Code activity
            Intent intent = new Intent(ProfileActivity.this, QRScreen.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        regEventsList = (ListView) findViewById(R.id.registeredEventsListView);


        //if(eventsArrayAdapter!=null)
        //regEventsList.setAdapter(eventsArrayAdapter);
        check_events();
    }



    public void check_events(){


        StringRequest postRequest = new StringRequest(Request.Method.POST, Utilities.url_event_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            file.writeToFile(response,"eventreglist.txt");
                            JSONObject jsonResponse = new JSONObject(response);
                            //Log.e("params",jsonResponse.toString());
                            int status = jsonResponse.getInt("status");
                            //String error = jsonResponse.getString("data");
                            //pDialog.dismiss();
                            switch (status) {
                                case 0:
                                    Toast.makeText(ProfileActivity.this, "There was a problem connecting to the server. Please check your username and password and try again.", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:

                                    JSONArray elist=jsonResponse.getJSONArray("data");
                                    Log.e("paramllist", elist.toString());
                                    int i=0;
                                    events.clear();
                                    while(i<elist.length())
                                    {
                                        JSONObject j=(JSONObject)elist.get(i);

                                        Log.e("param",j.toString());
                                        i++;
                                        events.add(j.get("event_name").toString());
                                    }

                                    eventsArrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.single_list_item, R.id.regevent, events) {
                                        @Override
                                        public View getView(final int position, View convertView, ViewGroup parent) {
                                            View view = super.getView(position, convertView, parent);
                                            fTextView textview = (fTextView) view;


                                            textview.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent in = new Intent(ProfileActivity.this, EventDetailActivity.class);
                                                    in.putExtra("eventName", events.get(position));
                                                    startActivity(in);
                                                }
                                            });

                                            return textview;

                                        }


                                    };


                                    lt.setVisibility(View.INVISIBLE);
                                    regEventsList.setAdapter(eventsArrayAdapter);
                                    if(events.isEmpty()){
                                        lt.setVisibility(View.VISIBLE);
                                        lt.setText("None.\n It's not too late to register for one!");
                                    }
                                    break;


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        lt.setText("Could not update :/");
                        String response = file.readFromFile("eventreglist.txt");
                        try {
                            file.writeToFile(response, "eventreglist.txt");
                            JSONObject jsonResponse = new JSONObject(response);
                            //Log.e("params",jsonResponse.toString());
                            int status = jsonResponse.getInt("status");
                            //String error = jsonResponse.getString("data");
                            //pDialog.dismiss();
                            switch (status) {
                                case 0:
                                    Toast.makeText(ProfileActivity.this, "There was a problem connecting to the server. Please check your username and password and try again.", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:

                                    JSONArray elist = jsonResponse.getJSONArray("data");
                                    Log.e("paramllist", elist.toString());
                                    int i = 0;
                                    events.clear();
                                    while (i < elist.length()) {
                                        JSONObject j = (JSONObject) elist.get(i);

                                        Log.e("param", j.toString());
                                        i++;
                                        events.add(j.get("event_name").toString());
                                    }


                                    eventsArrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.single_list_item, R.id.regevent, events) {
                                        @Override
                                        public View getView(final int position, View convertView, ViewGroup parent) {
                                            View view = super.getView(position, convertView, parent);
                                            fTextView textview = (fTextView) view;


                                            textview.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent in = new Intent(ProfileActivity.this, EventDetailActivity.class);
                                                    in.putExtra("eventName", events.get(position));
                                                    startActivity(in);
                                                }
                                            });

                                            return textview;

                                        }


                                    };

                                    lt.setVisibility(View.INVISIBLE);
                                    if(events.isEmpty()){
                                        lt.setVisibility(View.VISIBLE);
                                        lt.setText("None.\n It's not too late to register for one!");
                                    }
                                    regEventsList.setAdapter(eventsArrayAdapter);
                                    regEventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Toast.makeText(ProfileActivity.this,"ll",Toast.LENGTH_SHORT);
                                            Intent in = new Intent(ProfileActivity.this,EventDetailActivity.class);
                                            in.putExtra("eventName",events.get(i));
                                            startActivity(in);
                                        }
                                    });
                                    //Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                    //startActivity(i);
                                    //finish();
                                    break;


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("user_id",String.valueOf(Utilities.pid));
                params.put("user_pass", Utilities.pragyan_pass);
                return params;
            }
        };
        int socketTimeout = 10000;//10 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(postRequest);

    }

}