package com.mani.udacity.project.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thulasimanikandan_ra on 19-02-2018.
 */

public class MovieItem implements Parcelable {

    public String movieImage;
    public String movieTitle;
    public String movieUserRating;
    public String movieReleaseDate;
    public String movieOverview;
    public String movieOriginalTitle;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieImage);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieUserRating);
        dest.writeString(this.movieReleaseDate);
        dest.writeString(this.movieOverview);
        dest.writeString(this.movieOriginalTitle);
    }

    public MovieItem() {
    }

    private MovieItem(Parcel in) {
        this.movieImage = in.readString();
        this.movieTitle = in.readString();
        this.movieUserRating = in.readString();
        this.movieReleaseDate = in.readString();
        this.movieOverview = in.readString();
        this.movieOriginalTitle = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
