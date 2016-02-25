package org.pragyan.pragyantshirtapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notify extends ActionBarActivity {

    DBController controller = new DBController(this);
    List<Map<String, String>> sampleList;
    SampleAdapter sa;
    ArrayList<HashMap<String, String>> notifList;
    String notifs[],titles[];
    private String[] time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        final TextView em=(TextView) findViewById(R.id.empty);

        notifList = controller.getAllNotifs();
        if(notifList.size()>0)
        {
            em.setVisibility(View.INVISIBLE);
        }
        else
            em.setVisibility(View.VISIBLE);
        notifs=new String[notifList.size()];
        time=new String[notifList.size()];
        titles=new String[notifList.size()];
        for(int i=0;i<notifList.size();i++)
        {
            notifs[i]="null";
            time[i]="null";
            titles[i] = "null";
        }
        for(int i=0;i<notifList.size();i++)
        {
            notifs[i] = notifList.get(i).get("notifText");
            time[i]=notifList.get(i).get("time");
            titles[i] = notifList.get(i).get("title");
            Log.e("time", time[i]);

        }

        sampleList = new ArrayList<Map<String, String>>();
        sa=new SampleAdapter(this, R.layout.notif_item, sampleList);

        for (int i = notifList.size()-1; i >=0 ; i--) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(SampleAdapter.KEY_TEXT, notifs[i]);
            map.put(SampleAdapter.KEY_TIME,time[i]);
            map.put(SampleAdapter.KEY_TITLE,titles[i]);

            sampleList.add(map);
        }
        final ListAdapter listad= new SampleAdapter(this, R.layout.notif_item, sampleList);
        ListView listView = (ListView) findViewById(R.id.list_notif);
        listView.setAdapter(listad);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Utilities.strcolorsEvents[notifList.size()%Utilities.strcolorsEvents.length])));
        getSupportActionBar().setTitle("Notifications");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
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

    class SampleAdapter extends ArrayAdapter<Map<String, String>> {

        public static final String KEY_TEXT = "dept";
        public static final String KEY_TIME = "score";
        public static final String KEY_TITLE = "title";

        private final LayoutInflater mInflater;
        private final List<Map<String, String>> mData;

        public SampleAdapter(Context context, int layoutResourceId, List<Map<String, String>> data) {
            super(context, layoutResourceId, data);
            mData = data;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.notif_item, parent, false);
                viewHolder.text=(TextView)convertView.findViewById(R.id.notifText);
                viewHolder.time=(TextView)convertView.findViewById(R.id.notiftime);
                viewHolder.title=(fTextView)convertView.findViewById(R.id.notifytitle);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.text.setText(mData.get(position).get(KEY_TEXT));
            viewHolder.time.setText(mData.get(position).get(KEY_TIME));
            viewHolder.title.setText(mData.get(position).get(KEY_TITLE));
            convertView.setBackgroundColor(Color.parseColor(Utilities.strcolorsEvents[position % Utilities.strcolorsEvents.length]));


            return convertView;
        }

        class ViewHolder {
            fTextView title;
            TextView text;
            TextView time;
        }
    }

}
