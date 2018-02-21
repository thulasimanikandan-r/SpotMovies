package com.mani.udacity.project.popularmovie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mani.udacity.project.popularmovie.R;
import com.mani.udacity.project.popularmovie.constants.AppConstant;
import com.mani.udacity.project.popularmovie.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thulasimanikandan_ra on 19-02-2018.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesListViewHolder> {


    final private MovieListItemClickListener mOnItemClickListener;
    final private ArrayList<MovieItem> mMovieItems;
    private Context mContext;


    public MoviesRecyclerViewAdapter(MovieListItemClickListener mOnItemClickListener, ArrayList<MovieItem> mMovieItems) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.mMovieItems = mMovieItems;
    }

    @Override
    public MoviesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_movies_list, parent, false);
        return new MoviesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesListViewHolder holder, int position) {
        MovieItem item = mMovieItems.get(position);
        Picasso.with(mContext).load(AppConstant.IMAGE_URL + item.movieImage).into(holder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        return mMovieItems.size();
    }

    public void setMovieItemToAdapter(ArrayList<MovieItem> movieItems) {
        this.mMovieItems.addAll(movieItems);
    }


    class MoviesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView ivMovieImage;


        private MoviesListViewHolder(View itemView) {
            super(itemView);
            ivMovieImage = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnItemClickListener.onMovieItemClick(position);
        }
    }

    public interface MovieListItemClickListener {
        void onMovieItemClick(int position);
    }
}
