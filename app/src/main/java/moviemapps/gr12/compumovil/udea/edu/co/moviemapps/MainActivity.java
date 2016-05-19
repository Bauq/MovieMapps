package moviemapps.gr12.compumovil.udea.edu.co.moviemapps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment.FragmentListaPeliculas;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment.FragmentLogin;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment.MapaCinemasFragment;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment.MovieFragment;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener.OnFragmentInteractionListener;

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


        setFragment(FragmentLogin.ID, null, true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case(R.id.calificacion):
                break;
            case(R.id.armaPlan):
                setFragment(FragmentLogin.ID, null, true);
                break;
            case(R.id.mapa):
                setFragment(MapaCinemasFragment.ID, null, true);
                break;
            case(R.id.compartir):
                break;
            case(R.id.acercade):
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Bundle parametros, int accion) {

    }

    @Override
    public void setFragment(int fragmentId, Bundle parametros, boolean addStack) {
        Fragment f = null;
        switch (fragmentId) {
            case FragmentListaPeliculas.ID:
                f = FragmentListaPeliculas.newInstance();
                break;
            case MovieFragment.ID:
                parametros.getString(MovieFragment.ARG_PARAM1);
                f = MovieFragment.newInstance(parametros.getString(MovieFragment.ARG_PARAM1));
                break;
            case FragmentLogin.ID:
                f = FragmentLogin.newInstance();
                break;
            case MapaCinemasFragment.ID:
                f= MapaCinemasFragment.newInstance(0);
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, f, f.getClass().getName()).addToBackStack(f.getClass().getName());
        ft.commit();
    }

    public Context getContext(){
        return getApplicationContext();
    }


}
