package com.example.sss.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    ArrayList<newsData> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(getApplicationContext());
        getData();

        //initSwipePager();
    }

    private void getData(){

        String url ="https://newsapi.org/v1/articles?source=techcrunch&apiKey=d40a9cfd65f248678a9baa790e387fdc";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseData(String response) {
        Log.d("data",response);
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray dataArray = obj.getJSONArray("articles");
            for (int i = 0; i < dataArray.length();i++){
                JSONObject newsObj = dataArray.getJSONObject(i);
                dataArrayList.add(new newsData(newsObj.getString("author"), newsObj.getString("title"), newsObj.getString("description"),newsObj.getString("url"),newsObj.getString("urlToImage"), newsObj.getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initSwipePager(dataArrayList);

    }


    private void initSwipePager(ArrayList<newsData> data){
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this, data));
    }


}