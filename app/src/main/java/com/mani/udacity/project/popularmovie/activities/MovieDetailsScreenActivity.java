package com.mani.udacity.project.popularmovie.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mani.udacity.project.popularmovie.R;
import com.mani.udacity.project.popularmovie.constants.AppConstant;
import com.mani.udacity.project.popularmovie.model.MovieItem;
import com.squareup.picasso.Picasso;

public class MovieDetailsScreenActivity extends AppCompatActivity {

    TextView tvOriginalTitle;
    ImageView ivPoster;
    TextView tvUserRatings;
    TextView tvReleaseDate;
    TextView tvOverView;

    String mOriginalTitle;
    String mPoster;
    String mUserRatings;
    String mReleaseDate;
    String mOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_screen);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ivPoster = findViewById(R.id.iv_movie_details_poster);
        tvOriginalTitle = findViewById(R.id.tv_movie_details_title);
        tvUserRatings = findViewById(R.id.tv_movie_details_user_rating);
        tvReleaseDate = findViewById(R.id.tv_movie_details_release_date);
        tvOverView = findViewById(R.id.tv_movie_details_overview);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            MovieItem movieItem = bundle.getParcelable("movieItem");

            if (movieItem != null) {
                mOriginalTitle = movieItem.movieOriginalTitle;

                mOriginalTitle = movieItem.movieOriginalTitle;
                mPoster = movieItem.movieImage;
                mUserRatings = movieItem.movieUserRating + "/10";
                mReleaseDate = movieItem.movieReleaseDate;
                mOverview = movieItem.movieOverview;

                String[] date = mReleaseDate.split("-");

                tvOriginalTitle.setText(mOriginalTitle);
                tvUserRatings.setText(mUserRatings);
                tvReleaseDate.setText(date[0]);
                tvOverView.setText(mOverview);

                Picasso.with(this).load(AppConstant.IMAGE_URL + mPoster).into(ivPoster);
            }
        }
    }
}
