package co.edu.udea.moviemapps.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.fragment.FragmentListaPeliculas;
import co.edu.udea.moviemapps.fragment.FragmentLogin;
import co.edu.udea.moviemapps.fragment.MapaCinemasFragment;
import co.edu.udea.moviemapps.fragment.MovieFragment;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;

/**
 * Created by Samuel on 05/04/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(FragmentListaPeliculas.ID, null, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case(R.id.cines):
                setFragment(FragmentListaPeliculas.ID, null, true);
                break;
            case(R.id.armaPlan):
                setFragment(FragmentLogin.ID, null, true);
                break;
            case(R.id.mapa):
                setFragment(MapaCinemasFragment.ID, null, true);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setFragment(int fragmentId, Bundle parameters, boolean addStack) {
        Fragment f = null;
        switch (fragmentId) {
            case FragmentListaPeliculas.ID:
                f = FragmentListaPeliculas.newInstance();
                break;
            case MovieFragment.ID:
                parameters.getString(MovieFragment.ARG_ID_PELICULA);
                f = MovieFragment.newInstance(parameters.getString(MovieFragment.ARG_ID_PELICULA));
                break;
            case FragmentLogin.ID:
                f = FragmentLogin.newInstance();
                break;
            case MapaCinemasFragment.ID:
                f= MapaCinemasFragment.newInstance();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, f, f.getClass().getName()).addToBackStack(f.getClass().getName());
        ft.commit();
    }

}
