package com.mani.udacity.project.popularmovie.asynctasks;

import android.os.AsyncTask;

import com.mani.udacity.project.popularmovie.listeners.FetchMovieDetailsListener;
import com.mani.udacity.project.popularmovie.utils.NetworkUtil;

import java.io.IOException;
import java.net.URL;

/**
 * Created by thulasimanikandan_ra on 21-02-2018.
 */

public class FetchMovieDetailsTask extends AsyncTask<URL, Void, String> {

    private FetchMovieDetailsListener listener;

    public FetchMovieDetailsTask(FetchMovieDetailsListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String movieResponse = null;

        try {
            movieResponse = NetworkUtil.getResponseFromHttpUrl(searchUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieResponse;
    }

    @Override
    protected void onPostExecute(String movieResponse) {
        super.onPostExecute(movieResponse);
        if (movieResponse != null && !movieResponse.equals("")) {
            listener.getMovieDetailResponse(movieResponse);


        }
    }
}


