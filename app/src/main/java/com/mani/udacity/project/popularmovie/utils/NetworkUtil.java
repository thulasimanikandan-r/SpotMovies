package com.mani.udacity.project.popularmovie.utils;

import android.net.Uri;

import com.mani.udacity.project.popularmovie.constants.AppConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by thulasimanikandan_ra on 20-02-2018.
 */

public class NetworkUtil {


    public static URL buildUrl(String searchQuery) {
        Uri builtUri = Uri.parse(AppConstant.MOVIE_BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(searchQuery)
                .appendQueryParameter("api_key", AppConstant.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
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
