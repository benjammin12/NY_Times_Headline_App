package com.example.benjaminxerri.ny_times;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.example.benjaminxerri.dis_detector;

import com.example.benjaminxerri.ny_times.model.DisDetector;
import com.example.benjaminxerri.ny_times.model.NYTimes;
import com.example.benjaminxerri.ny_times.utilities.Network;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText headline;
    Button searchButton;
    ProgressBar progressBar;
    TextView results;
    static TextView startDate;
    static TextView endDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headline = (EditText) findViewById(R.id.text_to_analyze);
        searchButton = (Button) findViewById(R.id.search_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        results = (TextView) findViewById(R.id.results_text);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);

        //two anonymous classes to select the date
        new SelectDateFragment(this, startDate);
        new SelectDateFragment(this,endDate);


        searchButton.setOnClickListener(searchNyTimes);

    }




    private View.OnClickListener searchNyTimes = new View.OnClickListener() {
        public void onClick(View v) {

            String dateStartInput = startDate.getText().toString();
            String dateEndInput = endDate.getText().toString();

            SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat endFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date start = null;
            Date end = null;


            try{
                start = startFormat.parse(dateStartInput);
                end = startFormat.parse(dateEndInput);
            }catch(ParseException e){
                results.setText("You need to enter a valid date");
                return;
            }

            String formattedStartDate = endFormat.format(start);
            String formattedEndDate = endFormat.format(end);


            String searchHeadline = headline.getText().toString();
            if (searchHeadline != null && !searchHeadline.equals("")){
                URL url = Network.buildURL(searchHeadline, formattedStartDate.replace("/","") ,formattedEndDate.replace("/",""));
                Log.d("URl is", url.toString());
                new SearchNyTimes().execute(url);
            }
            else {
                results.setText("You must enter a headline to search");
            }

        }
    };

    public class SearchDis extends AsyncTask<URL,Void,String> {
        @Override
        protected String doInBackground(URL... urls) {
            String toReturn = "DID NOT WORK";
            try {
                Log.v("Logging url:", urls[0].toString());
                toReturn = Network.getResponseFromHttpUrl(urls[0]);

            } catch (Exception e) {
                Log.d("Error", "exception on get Response from HTTP call" + e.getMessage());
                return null;
            }

            return toReturn;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        //what do in background returns,
        // onPostExecute must take in,
        // because doInBackground automatically calls onPostExecute

        @Override
        protected void onPostExecute (String s){
            progressBar.setVisibility(View.INVISIBLE);
            if(s != null && !s.equals("")){
                s = new DisDetector().parseJson(s);
                results.append(s);
            }
            else {
                results.append("Error Building Sentiment Analysis Text");
            }
        }

    }


    public class SearchNyTimes extends AsyncTask<URL, Void, String>{
        String toReturn = "DID NOT WORK";
        @Override
        protected String doInBackground(URL... url) {

            try {
                Log.d("Logging url:", url[0].toString());
                toReturn = Network.getResponseFromHttpUrl(url[0]);

            } catch (Exception e) {
                Log.d("Error", "exception on get Response from HTTP call" + e.getMessage());
                return null;
            }

            return toReturn;

        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            if(s != null && !s.equals("")) {
                String searchHeadline = headline.getText().toString();
                String dateStartInput = startDate.getText().toString();
                String dateEndInput = endDate.getText().toString();

                //creates anonymouse instance of NyTimes class and parses the response of the HTTP Request
               List<String> jsonData =  new NYTimes().parseJson(s);
                results.append("\n" + "Headlines for " + searchHeadline + " from " + dateStartInput + "-"
                        + dateEndInput + "! \n"+ jsonData.toString() + "\n\n");
                URL meaningCloudUrl = Network.buildMeaningCloudURL(jsonData.toString());
                new SearchDis().execute(meaningCloudUrl);

            }else{
                results.setText("Error Fetching Times Articles");
            }
        }
    }

}
    /**One way to display calendar using an inner class, but refactored to a seperate class because the project needed two fragments


    public static class SelectStartDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            startDate.setText(month+"/"+day+"/"+year);
        }
    }

    */


