package moviemapps.gr12.compumovil.udea.edu.co.moviemapps;

/**
 * Created by Carolina on 11/04/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener.OnItemMovieListener;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Movie> movies;
    private OnItemMovieListener listener;


    public MyAdapter(ArrayList<Movie> myDataset, OnItemMovieListener listener) {
        movies = myDataset;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pelicula, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(movies.get(position) != null) {
            holder.tvNombre.setText(movies.get(position).getTitle());
            holder.tvDuracion.setText(String.valueOf(movies.get(position).getReleaseDate()));
            holder.tvGenero.setText(String.valueOf(movies.get(position).getId()));
            if (movies.get(position).getPosterPath() != null) {
                downlad(holder.ivPoster, movies.get(position).getPosterPath());
            }

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(movies.get(position), holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDuracion,tvGenero;
        static ImageView ivPoster;


        public ViewHolder(View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            tvNombre = (TextView) itemView.findViewById(R.id.tv_nombre_pelicula);
            tvDuracion = (TextView) itemView.findViewById(R.id.tv_duracion);
            tvGenero= (TextView) itemView.findViewById(R.id.tv_genero);

        }
    }

    private void downlad(final ImageView imageView, String url){
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String BASEURLIMAGEN = "http://image.tmdb.org/t/p/w500";
                URL imageUrl;
                Bitmap imagen = null;
                try {
                    imageUrl = new URL(BASEURLIMAGEN + params[0]);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    imagen = BitmapFactory.decodeStream(conn.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return imagen;
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
