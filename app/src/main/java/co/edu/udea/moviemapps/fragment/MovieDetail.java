package co.edu.udea.moviemapps.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.persistence.ClassificationDataManager;
import co.edu.udea.moviemapps.rest.MovieDBService;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import co.edu.udea.moviemapps.util.MovieMappsUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail extends Fragment implements View.OnClickListener {
    public static final int ID = 2;
    public static final String MOVIE_ARG_ID = "movieId";
    public String apiKey = "d4aadc42b63f7a1565bffa6dd41f1bfc";
    public Classification classification;
    public List<Classification> classifications;
    public ImageView moviePoster;
    public ImageButton like, dislike;
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
        like  = (ImageButton) view.findViewById(R.id.like);
        dislike = (ImageButton) view.findViewById(R.id.dislike);
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        new DownloadMovie().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like:
                classification = new Classification();
                classification.setIdMovie(Integer.valueOf(movieId));
                classification.setIdUsuario(MovieMapps.getUser().getId());
                classification.setValor(Integer.parseInt("1"));
                ClassificationDataManager.getInstance().saveClassification(classification);
                break;
            case R.id.dislike:
                classification = new Classification();
                classification.setIdMovie(Integer.valueOf(movieId));
                classification.setIdUsuario(MovieMapps.getUser().getId());
                classification.setValor(Integer.parseInt("2"));
                ClassificationDataManager.getInstance().saveClassification(classification);
                break;
        }
    }

    public void setClassification(ServiceResult classification) {
        if(classification != null) {
            this.classifications = (List<Classification>) classification;
        }
    }

    private class DownloadMovie extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            Call<Movie> results = MovieDBService.getInstance().movieById(movieId, apiKey);
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
            DownloadClassification(String.valueOf(movie.getId()));
            downloadImage(moviePoster, movie.getPosterPath());
        }
    }

    private void downloadImage(final ImageView imageView, String url) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                Log.d("PARAMS", "doInBackground: "+ params[0]);
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

    private void DownloadClassification (final String idMovie) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            protected Void doInBackground(String... params) {
                Call<ServiceResult> results = MovieMappsService.getInstance().getClassificationMovie(idMovie);
                results.enqueue(new Callback<ServiceResult>() {
                    @Override
                    public void onResponse(Call<ServiceResult> call, Response<ServiceResult> response) {
                        Log.e("Calificaciones", response.message());
                        setClassification(response.body());
                    }

                    @Override
                    public void onFailure(Call<ServiceResult> call, Throwable t) {
                    }
                });
                return null;
            }
        };
        asyncTask.execute(idMovie);
    }
}

