package co.edu.udea.moviemapps.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import co.edu.udea.moviemapps.util.MovieMappsUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail extends Fragment {
    public static final int ID = 2;
    public static final String MOVIE_ARG_ID = "movieId";
    public String apiKey = "d4aadc42b63f7a1565bffa6dd41f1bfc";

    public ImageView moviePoster;
    public TextView movieTitle, movieOverview;
    private String movieId;

    public static MovieDetail newInstance(String movieId) {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.movieId = movieId;
        return movieDetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(MOVIE_ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviePoster = (ImageView) view.findViewById(R.id.poster);
        movieOverview = (TextView) view.findViewById(R.id.overview);
        movieTitle = (TextView) view.findViewById(R.id.movie_title);
        new DownloadMovie().execute();
    }

    private class DownloadMovie extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            Call<Movie> results = MovieMappsService.getInstance().movieById(movieId, apiKey);
            results.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    setMovie(response.body());
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
            return response;
        }
    }

    public void setMovie(Movie movie) {
        if (movie != null) {
            movieTitle.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            downloadImage(moviePoster, movie.getPosterPath());
        }
    }

    private void downloadImage(final ImageView imageView, String url) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
               return MovieMappsUtils.getImage(params);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(url);
    }
}
