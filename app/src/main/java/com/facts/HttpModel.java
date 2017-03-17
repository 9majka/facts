package com.facts;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpModel {
    private static final String TAG = "HttpModel";

    // Best topic : http://muzey-factov.ru/best/from10
    private static final String mUrlBest = "http://muzey-factov.ru/best/from";
    private static final String mUrlNew = "http://muzey-factov.ru/from";

    private static HttpModel sInstance = null;
    private HttpModelObserver mObserver = null;

    public static HttpModel getInstance() {
        if (sInstance == null) {
            sInstance = new HttpModel();
        }
        return sInstance;
    }

    private HttpModel() {
    }

    public void setObserver(HttpModelObserver observer) {
        mObserver = observer;
    }

    private FactItems loadInternal(String urlString, int from) {
        FactItems items = null;
        urlString = urlString + from;
        Log.e(TAG, "loadInternal: urlString = " + urlString);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setUseCaches(true);

            InputStream is = urlConnection.getInputStream();
            HTMLParser parser = new HTMLParser(is);
            items = parser.parse();

        } catch (IOException e) {
            Log.e(TAG, "loadInternal: Exception " + e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return items;
    }

    public void loadBest(int from) {
        FactsLoaderTask task = new FactsLoaderTask(mUrlBest, from);
        task.execute();
    }
    public void loadNew(int from) {
        FactsLoaderTask task = new FactsLoaderTask(mUrlNew, from);
        task.execute();
    }

    private class FactsLoaderTask extends AsyncTask<Void, Void, FactItems> {
        private String mUrl;
        private int mFrom;

        FactsLoaderTask(String url, int from) {
            mUrl = url;
            mFrom = from;
        }

        @Override
        protected FactItems doInBackground(Void... params) {
            return loadInternal(mUrl, mFrom);
        }

        @Override
        protected void onPostExecute(FactItems factItems) {
            super.onPostExecute(factItems);
            if(mObserver != null) {
                mObserver.onFactsReady(factItems);
            }
        }
    }
}