package com.mani.udacity.project.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mani.udacity.project.popularmovie.activities.MovieDetailsScreenActivity;
import com.mani.udacity.project.popularmovie.adapters.MoviesRecyclerViewAdapter;
import com.mani.udacity.project.popularmovie.listeners.FetchMovieDetailsListener;
import com.mani.udacity.project.popularmovie.asynctasks.FetchMovieDetailsTask;
import com.mani.udacity.project.popularmovie.model.MovieItem;
import com.mani.udacity.project.popularmovie.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.MovieListItemClickListener, FetchMovieDetailsListener {

    private ProgressBar mLoadMovieProgressBar;
    private ArrayList<MovieItem> mMovieItemList;

    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;
    private RecyclerView mMoviePosterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviePosterRecyclerView = findViewById(R.id.rv_numbers);
        mLoadMovieProgressBar = findViewById(R.id.pb_load_movie_details);
        mMovieItemList = new ArrayList<>();

        initializeMovieRecyclerViewAdapter();
        displayMovieDetails("popular");

    }

    private void displayMovieDetails(String sortBy) {

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            new FetchMovieDetailsTask(this).execute(NetworkUtil.buildUrl(sortBy));
            mLoadMovieProgressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeMovieRecyclerViewAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mMoviePosterRecyclerView.setLayoutManager(gridLayoutManager);
        mMoviePosterRecyclerView.setHasFixedSize(true);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(this, mMovieItemList);
        mMoviePosterRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
    }


    @Override
    public void onMovieItemClick(int position) {
        MovieItem movieItem = mMovieItemList.get(position);

        Intent intent = new Intent(this, MovieDetailsScreenActivity.class);
        intent.putExtra("movieItem", movieItem);
        startActivity(intent);
    }

    @Override
    public void getMovieDetailResponse(String response) {
        parsePosterResult(response);

        mMoviesRecyclerViewAdapter.setMovieItemToAdapter(mMovieItemList);
        mMoviesRecyclerViewAdapter.notifyDataSetChanged();
        mLoadMovieProgressBar.setVisibility(View.INVISIBLE);
    }

    private void parsePosterResult(String mResponse) {
        mMovieItemList.clear();
        try {
            JSONObject response = new JSONObject(mResponse);
            String total_results = response.optString("total_results");
            Log.d("PopularMovieApp", "Total Results : " + total_results);
            JSONArray results = response.optJSONArray("results");
            MovieItem item;
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.optJSONObject(i);
                item = new MovieItem();
                item.movieTitle = result.optString("title");
                item.movieImage = result.optString("poster_path");
                item.movieOriginalTitle = result.optString("original_title");
                item.movieReleaseDate = result.optString("release_date");
                item.movieUserRating = result.optString("vote_average");
                item.movieOverview = result.optString("overview");

                mMovieItemList.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_movie_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.sort_menu_by_popular:
                displayMovieDetails("popular");
                break;
            case R.id.sort_menu_by_top_rated:
                displayMovieDetails("top_rated");
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
