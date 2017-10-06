package com.example.benjaminxerri.ny_times.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by benjaminxerri on 9/16/17.
 */

public class Network {
    final static String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    final static String API_KEY = "4bc349cc561c4e5d8e7023eff1bf7019";
    final static String BEGIN_DATE = "begin_date";
    final static String END_DATE = "end_date";
    final static String MEANING_CLOUD_API_KEY = "5ba5d3a63dff25195392c59733959af7";
    static URL url = null;

    public static URL buildURL(String searchQuery, String beginDate, String endDate) {
        //build URI that takes in the base as a query param, so the user can enter the base currency
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("api-key",API_KEY)
                .appendQueryParameter("fq", searchQuery)
                .appendQueryParameter(BEGIN_DATE, beginDate)
                .appendQueryParameter(END_DATE, endDate)
                .build();
        try {
           url = new URL(buildUri.toString());
            return url;
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL buildMeaningCloudURL(String inputValue) {
        //build URI that takes in the base as a query param, so the user can enter the base currency
        Uri buildUri = Uri.parse("https://api.meaningcloud.com/sentiment-2.1").buildUpon()
                .appendQueryParameter("key",MEANING_CLOUD_API_KEY)
                .appendQueryParameter("txt", inputValue)
                .appendQueryParameter("lang", "en")
                .build();
        try {
            url = new URL(buildUri.toString());
            return url;
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
