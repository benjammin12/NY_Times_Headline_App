package com.example.benjaminxerri.ny_times.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjaminxerri on 9/29/17.
 */

public class NYTimes {
    private List<String> timesArticles;

    public NYTimes(){
        timesArticles = new ArrayList<String>();
    }


    public List<String> getTimesArticle() {
        return timesArticles;
    }


    public List<String> parseJson(String jsonText) {
        //display the "score_tag", the "confidence" and the "irony" tag along
        Log.v("Parsing json object" , jsonText);
        String results;


        try {
            JSONObject NyTimesResponse = new JSONObject(jsonText);
            JSONObject NyTimesObject = NyTimesResponse.getJSONObject("response");
            JSONArray NyTimesArray = NyTimesObject.getJSONArray("docs");

            for(int i = 0; i < NyTimesArray.length(); i++){
                JSONObject headline = NyTimesArray.getJSONObject(i);
                //String name = headline.getString("headline");
                JSONObject main = headline.getJSONObject("headline");
                String mainHeadline = main.getString("main");
                timesArticles.add(mainHeadline);
            }

            return timesArticles;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


}
