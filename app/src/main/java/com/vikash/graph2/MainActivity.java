package com.vikash.graph2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnBarChart, btnPieChart,btnLineChart,btn;
    private RequestQueue requestQueue;
    private  TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

// Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);
        String url ="https://randomuser.me/api/";
        //jsonParse(url);


        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        btnBarChart = findViewById(R.id.btnBarChart);
        btnLineChart = findViewById(R.id.btnLineChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, BarChartActivity.class);
                startActivity(I);
            }
        });
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, PieChartActivity.class);
                startActivity(I);
            }
        });
        btnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(I);
            }
        });
    }
    private void jsonParse(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    JSONObject dob=jsonArray.getJSONObject(0);
                    JSONObject example = dob.getJSONObject("dob");

                    Log.e("DOB",example.toString());
                    String open,low,high;
                    open=example.getString("age");


                    textView.setText(open);

//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject employee = jsonArray.getJSONObject(i);
//                        String firstName = employee.getString("firstname");
//                        int age = employee.getInt("age");
//                        String mail = employee.getString("mail");
//                        textView.append(firstName + ", " + String.valueOf(age) + ", " + mail +"\n\n");
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

}
