package org.pragyan.pragyantshirtapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GCMRegisterService extends IntentService {
    static Context context;
    static String msg;
    public static void register(Context cont) {
        context = cont;
        Intent intent = new Intent(cont, GCMRegisterService.class);
        cont.startService(intent);
    }

    public GCMRegisterService() {
        super("GCMRegisterService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            final String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("gcm_status",token.toString());
            String serverUrl = Utilities.url_gcm;
            Log.e("gcm_status", "Attempt to register");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    msg = response;
                    try {

                        JSONObject js = new JSONObject(response);
                        int gcm = js.getInt("status_code");
                        Utilities.gcm_registered = 1;
                        SharedPreferences.Editor editor = Utilities.sp.edit();
                        editor.putInt("gcm_registered", Utilities.gcm_registered);
                        editor.apply();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.e("gcm_status", "Failed to register :" + error);
                    new Handler().post(new DisplayToast(context, "Please check your internet and Try again"));
                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:
                    params.put("p_id", String.valueOf(Utilities.pid));
                    params.put("gcm_id", token);
                    return params;
                }
            };
            // Add the request to the queue
            int socketTimeout = 10000;//10 seconds
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            Volley.newRequestQueue(context).add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
