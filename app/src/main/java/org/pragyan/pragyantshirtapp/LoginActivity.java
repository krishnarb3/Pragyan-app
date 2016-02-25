package org.pragyan.pragyantshirtapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends ActionBarActivity {


    String emailIdString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button signInButton = (Button) findViewById(R.id.signInButton);
        TextView registerText = (TextView) findViewById(R.id.registerText);


        final EditText emailIdEdit, passwordEdit;
        emailIdEdit = (EditText) findViewById(R.id.emailIdEdit);
        passwordEdit = (EditText) findViewById(R.id.password);

        CheckBox checkBox = (CheckBox)findViewById(R.id.showPasswordCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if (!isChecked) {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIdString = emailIdEdit.getText().toString();
                passwordString = passwordEdit.getText().toString();
                if (emailIdString.length() == 0) emailIdEdit.setError("Invalid username");
                else if (passwordString.length() == 0) passwordEdit.setError("Invalid password");
                else performSignIn();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(i);
            }
        });
    }

    public void performSignIn() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Utilities.url_auth,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            String error = jsonResponse.getString("data");
                            pDialog.dismiss();
                            switch (status) {
                                case 0:
                                    Toast.makeText(LoginActivity.this, "There was a problem connecting to the server. Please check your username and password and try again.", Toast.LENGTH_LONG).show();
                                    break;
                                case 1:
                                    //STORING USER LOGIN DATA IN SHARED PREFERENCES
                                    SharedPreferences.Editor editor = Utilities.sp.edit();
                                    Utilities.status = 1;
                                    editor.putInt("status", Utilities.status);
                                    editor.putString("pragyan_mail", emailIdString);
                                    Utilities.pragyan_mail = emailIdString;
                                    editor.putString("pragyan_pass", passwordString);
                                    Utilities.pragyan_pass = passwordString;
                                    editor.apply();
                                    getDetails();
                                    return;

                                case 3:
                                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
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
                        pDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Please check your internet and Try again", Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("user_email", emailIdString);
                params.put("user_pass", passwordString);
                return params;
            }
        };
        int socketTimeout = 10000; //10 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void getDetails(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching User Details...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Utilities.url_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            JSONObject jsondata = jsonResponse.getJSONObject("data");
                            pDialog.dismiss();
                            switch (status) {
                                case 0:
                                    Toast.makeText(LoginActivity.this, "There was a problem connecting to the server. Please check your username and password and Try again.", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    int user_id = jsondata.getInt("user_id");
                                    String user_name = jsondata.getString("user_name");
                                    String user_fullname = jsondata.getString("user_fullname");
                                    Log.i("details",String.valueOf(user_id)+" : "+user_name+" : "+user_fullname);
                                    //STORING USER LOGIN DATA IN SHARED PREFERENCES
                                    SharedPreferences.Editor editor = Utilities.sp.edit();
                                    Utilities.status = 1;
                                    editor.putInt("status", Utilities.status);
                                    editor.putString("name", user_name);
                                    Utilities.name = user_name;
                                    editor.putString("fullname", user_fullname);
                                    Utilities.fullname = user_fullname;
                                    editor.putInt("pid", user_id);
                                    Utilities.pid = user_id;
                                    editor.apply();
                                    if(Utilities.gcm_registered == 0)
                                        GCMRegisterService.register(LoginActivity.this);
                                    getQR();
                                    break;

                                case 3:
                                    Toast.makeText(LoginActivity.this, "There was a problem connecting to the server. Please check your username and password and Try again.", Toast.LENGTH_LONG).show();
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
                        pDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Please check your internet and Try again", Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("user_email", emailIdString);
                params.put("user_pass", passwordString);
                return params;
            }
        };
        int socketTimeout = 10000; //10 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(postRequest);

    }

    public void getQR(){
        if(Utilities.pragyan_mail.endsWith("@nitt.edu"))
            new qrAsyncTask().execute();
        else
            GetQRNonNitt();

    }

    public void GetQRNonNitt() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting QR...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.pragyan.org/quadtest/getQR", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                byte[] imageAsBytes = Base64.decode(response.getBytes(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                SaveImage save =  new SaveImage(String.valueOf(Utilities.pid), null);
                save.saveToCacheFile(bitmap);
                addImageToGallery(save.getCacheFilename(), LoginActivity.this);

                Toast.makeText(LoginActivity.this, "QR saved to gallery", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, "Please check your internet and try again", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("id", String.valueOf(Utilities.pid));
                return params;
            }

        };

        int socketTimeout = 10000;//10 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    class qrAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog myPd_ring = null;

        @Override
        protected void onPreExecute() {

            myPd_ring = new ProgressDialog(LoginActivity.this);
            myPd_ring.setMessage("Getting QR...");
            myPd_ring.show();

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpClient httpclient = new DefaultHttpClient();
            Bitmap image = null;
            HttpPost httppost = new HttpPost(Utilities.url_qr);
            try {
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("user_roll", Utilities.name));
                nameValuePairs.add(new BasicNameValuePair("user_pass", Utilities.pragyan_pass));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity httpEntity = response.getEntity();
                byte[] img = EntityUtils.toByteArray(httpEntity);
                image = BitmapFactory.decodeByteArray(img, 0, img.length);


            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }
            return image;


        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);
            myPd_ring.dismiss();
            if(bmp!=null) {
                SaveImage save = new SaveImage(String.valueOf(Utilities.pid), null);
                save.saveToCacheFile(bmp);
                addImageToGallery(save.getCacheFilename(), LoginActivity.this);

                Toast.makeText(LoginActivity.this, "QR saved to gallery", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else{
                new Handler().post(new DisplayToast(LoginActivity.this,"QR not received"));
            }
        }
    }
}
