package com.wszh.weitraininghttpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mUrlEditText;
    private TextView mResultTextView;
    public static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrlEditText = (EditText) findViewById(R.id.activity_main_edittext);
        mResultTextView = (TextView) findViewById(R.id.activity_main_textview);

    }

    public void myClickHandler(View view) {
        ConnectivityManager activityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = activityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "dowlong.......", Toast.LENGTH_SHORT).show();
            new DownloadWebpageTask().execute(mUrlEditText.getText().toString());

        } else {
            mResultTextView.setText("No network");
        }
    }

    class DownloadWebpageTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return downloadUrl(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            /*
            catch (IOException e) {
                e.printStackTrace();
            }
            */
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mResultTextView.setText(s);
        }

        private String downloadUrl(String url) throws MalformedURLException {
            URL myUrl = new URL(url);
            InputStream is=null;
            HttpURLConnection conn=null;
            int len=500;

            try {
                conn=(HttpURLConnection) myUrl.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response=conn.getResponseCode();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
