package com.example.benjaminxerri.ny_times.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benjaminxerri on 9/29/17.
 */

public class DisDetector {
    private String disAnalysisResults;

    public DisDetector(){
        disAnalysisResults = "";
    }

    public DisDetector(String disText){
        setDisText(disText);
    }

    public String getDisText() {
        return disAnalysisResults;
    }

    public void setDisText(String disText) {
        disAnalysisResults = disText;
    }

    public String parseJson(String jsonText) {
        //display the "score_tag", the "confidence" and the "irony" tag along
        Log.v("Parsing json object" , jsonText);

        try {
            JSONObject meaningResponse = new JSONObject(jsonText);
            String score_tag = meaningResponse.getString("score_tag");
            String confidence = meaningResponse.getString("confidence");
            String irony = meaningResponse.getString("irony");

            score_tag = parseScoreTag(score_tag);

            disAnalysisResults = "Score tag: "+score_tag + "\n" +
                             "Confidence: " + confidence + "\n" +
                              "Irony: " + irony;

            return disAnalysisResults;
        }catch (JSONException e){
            e.printStackTrace();
            disAnalysisResults = "Error parsing string";
            return disAnalysisResults;
        }
    }

    private String parseScoreTag(String s){
        String results = "";
        switch (s){
            case "P+":
                results = "Strong Positive";
                break;
            case "P":
                results = "Positive";
                break;
            case "NEU":
                results = "Neutral";
                break;
            case "N":
                results = "Negative";
                break;
            case "N+":
                results = "Strong Negative";
                break;
            case "NONE":
                results = "Without Sentiment.";
                break;
            default:
                results = "Error parsing results";
                break;

        }
        return results;
    }

}
