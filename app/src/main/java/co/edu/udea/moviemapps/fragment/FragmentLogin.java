package co.edu.udea.moviemapps.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Usuario;
import co.edu.udea.moviemapps.persistence.UsuarioDataManager;

/**
 * Created by Sebastian on 11/04/2016.
 */
public class FragmentLogin extends Fragment {
    public static final int ID = 3;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    Toast toast;
    TextView nombreUsuario;
    ImageView imagenUsuario;
    Usuario usuario;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    private void mostrarUsuario(Profile currentProfile) {
        usuario = MovieMapps.getUsuario();
        if (usuario != null) {
            cargarImagen.execute(usuario.getFoto());
            nombreUsuario.setText(usuario.getNombre());
            toast = Toast.makeText(MovieMapps.getContext(), usuario.getNombre(), Toast.LENGTH_LONG);
            toast.show();
        } else if (currentProfile != null) {
            usuario = new Usuario();
            usuario.setNombre(currentProfile.getName());

            Uri uriFotoPerfil = currentProfile.getProfilePictureUri(200, 200);
            cargarImagen.execute(uriFotoPerfil.toString());
            usuario.setFoto(uriFotoPerfil.toString());
            actualizarUsuario(usuario);
            toast = Toast.makeText(MovieMapps.getContext(), usuario.getNombre(), Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void actualizarUsuario(Usuario user) {
        this.usuario = user;
        MovieMapps.setUsuario(user);
        UsuarioDataManager.getInstance().update(this.usuario);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(MovieMapps.getContext());
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        nombreUsuario = (TextView) view.findViewById(R.id.nombre);
        imagenUsuario = (ImageView) view.findViewById(R.id.imagen);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    usuario = MovieMapps.getUsuario();
                                    usuario.setId(Long.valueOf(object.getString("id")));
                                    usuario.setCorreo(object.getString("email"));
                                    actualizarUsuario(usuario);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("login", "cancelado");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("login", exception.getMessage());
            }
        });
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                mostrarUsuario(oldProfile);
                mostrarUsuario(currentProfile);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    AsyncTask<String, Void, Bitmap> cargarImagen = new AsyncTask<String, Void, Bitmap>() {
        @Override
        protected Bitmap doInBackground(String... params) {

            URL imageUrl;
            Bitmap imagen = null;
            try {
                imageUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                Toast.makeText(MovieMapps.getContext(), "Error cargando la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imagenUsuario.setImageBitmap(bitmap);
            this.cancel(true);
        }
    };
}
