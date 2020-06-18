package com.vikash.graph2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView textView;
    private LineChart chart;
    private String url ="https://randomuser.me/api/";
    private List<Entry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        textView = (TextView) findViewById(R.id.text);
        chart = findViewById(R.id.linechart);
        requestQueue = Volley.newRequestQueue(this);
        entries = new ArrayList<Entry>();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 500; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jsonParse(url);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

    }


    private void addEntry(int val){
//        float val = (float) (Math.random() * 10) - 30;
        entries.add(new Entry(entries.size(), val, getResources().getDrawable(R.drawable.star)));
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        Log.e("Entries", String.valueOf(entries));
        Log.e("Dataset", String.valueOf(dataSet));
        dataSet.setColor(Color.RED);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
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

                    addEntry(Integer.parseInt(open));

                    //textView.setText(open);

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
