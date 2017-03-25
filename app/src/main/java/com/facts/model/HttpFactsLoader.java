package com.facts.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.facts.FactItem;
import com.facts.FactItems;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpFactsLoader implements IFactsLoader {
    private static final String TAG = "HttpFactsLoader";

    // Best topic : http://muzey-factov.ru/best/from10
    private static final String mUrlBest = "http://muzey-factov.ru/best/from";
    private static final String mUrlNew = "http://muzey-factov.ru/from";
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) "
                                                   + "AppleWebKit/537.36 (KHTML, like Gecko) "
                                                   + "Chrome/52.0.2743.82 Safari/537.36";
    private final int TIMEOUT = 10000;

    private static HttpFactsLoader sInstance = null;
    private FactsLoaderCallbacks mObserver = null;

    public static HttpFactsLoader getInstance() {
        if (sInstance == null) {
            sInstance = new HttpFactsLoader();
        }
        return sInstance;
    }

    private HttpFactsLoader() {
    }

    public void setObserver(FactsLoaderCallbacks observer) {
        mObserver = observer;
    }

    private FactItems loadInternal(String urlString, int from) {
        FactItems items = null;
        urlString = urlString + from;
        Log.i(TAG, "loadInternal: urlString = " + urlString);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            urlConnection.setUseCaches(true);

            InputStream is = urlConnection.getInputStream();
            Log.i(TAG, "loadInternal: is = " + is);
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

    private class FactsLoaderTask extends AsyncTask<Void, FactItems, FactItems> {
        private String mUrl;
        private int mFrom;

        FactsLoaderTask(String url, int from) {
            mUrl = url;
            mFrom = from;
        }

        private void loadImages(FactItems factItems) {
            for (FactItem factItem : factItems) {
                Bitmap bm = null;
                HttpURLConnection urlConnection = null;
                try {
                    URL aURL = new URL(factItem.getImgUrl());
                    urlConnection = (HttpURLConnection) aURL.openConnection();
                    urlConnection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
                    urlConnection.setUseCaches(true);
                    urlConnection.setConnectTimeout(TIMEOUT);
                    urlConnection.setReadTimeout(TIMEOUT);
                    urlConnection.connect();
                    InputStream is = urlConnection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    factItem.setBitmap(bm);
                    bis.close();
                    is.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected FactItems doInBackground(Void... params) {
            FactItems facts = loadInternal(mUrl, mFrom);

            if(facts != null) {
                publishProgress(facts);
                loadImages(facts);
            }

            return facts;
        }

        @Override
        protected void onProgressUpdate(FactItems... values) {
            super.onProgressUpdate(values);
            FactItems items = null;
            if(values != null) {
                items = values[0];
            }
            Log.i(TAG, "onProgressUpdate " + mObserver);
            if(mObserver != null) {
                if(items != null) {
                    mObserver.onFactsCreated(items);
                } else {
                    mObserver.onError();
                }
            }
        }

        @Override
        protected void onPostExecute(FactItems factItems) {
            super.onPostExecute(factItems);
            Log.i(TAG, "onPostExecute" + mObserver);
            if (mObserver != null) {
                if (factItems != null) {
                    mObserver.onFactsUpdated(factItems);
                } else {
                     mObserver.onError();
                }
            }
        }
    }
}