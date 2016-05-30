package co.edu.udea.moviemapps.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;
import co.edu.udea.moviemapps.model.Cinema;

/**
 * Created by Brian on 05/05/2016.
 */
public class MapaCinemasFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener {

    public static final int ID = 8;

    private static final int FAST_GPS_UPDATE = 3000;
    private static final int SLOW_GPS_UPDATE = 30000;
    private static final int MIN_DISTANCE_GPS_UPDATES = 0;
    private static boolean fast = false;
    private static float zoom = (float) 15.5;
    private static DecimalFormat twoDForm = new DecimalFormat("#.##");
    private static OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private Double myLat = 6.2676721377548335, myLng = -75.56773066520691;
    private LatLng myLatLng;
    private boolean marcar = true;

    private List<Cinema> cinemaList = new ArrayList<>();
    private static View rootView;

    public static MapaCinemasFragment newInstance() {
        return new MapaCinemasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_mapa_cinemas, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
            // map is already there, just return view as it is
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = (OnFragmentInteractionListener) getActivity();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        cargarPosition();
    }


    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLng = location.getLongitude();
        myLatLng = new LatLng(myLat, myLng);
        if (marcar == true) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoom));
            marcar = false;
            marcarCinemas();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Provider Change: ", provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("Provider Enabled: ", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("Provider Disabled: ", provider);
    }


    private void cargarCinemas() {
        List<Cinema> listaCinemas = new ArrayList<>();
        Cinema c = new Cinema();
        c.setName("Cine Colombia Los Molinos");
        c.setLatitude(6.232321400000001);
        c.setLongitude(-75.60410279999996);
        listaCinemas.add(c);
        c = new Cinema();
        c.setName("Procinal Puerta del norte");
        c.setLatitude(6.339409599999999);
        c.setLongitude(-75.54321419999997);
        listaCinemas.add(c);
        c = new Cinema();
        c.setName("Procinal Florida");
        c.setLatitude(6.270901899999999);
        c.setLongitude(-75.57674639999999);
        listaCinemas.add(c);
        c = new Cinema();
        c.setName("Royal Films Bosque Plaza");
        c.setLatitude((double) 6.268674619223301);
        c.setLongitude((double) -75.56475877761841);
        listaCinemas.add(c);
        cinemaList = listaCinemas;
        marcarCinemas();
    }


    private static double distancia(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; //kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }


    private void marcarCinemas() {

        LatLng LatLngCine;
        Cinema cine;
        String unidad;
        double d;

        for (int i = 0; i < cinemaList.size(); i++) {
            cine = cinemaList.get(i);
            LatLngCine = new LatLng(cine.getLatitude(), cine.getLongitude());
            d = distancia(myLat, myLng, cinemaList.get(i).getLatitude(), cinemaList.get(i).getLongitude());
            if (d < 1) {
                d = d * 1000;
                unidad = " m";
            } else {
                unidad = " km";
            }
            mMap.addMarker(new MarkerOptions()
                    .position(LatLngCine)
                    .title(cine.getName())
                    .snippet(twoDForm.format(d) + unidad)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        }

    }

    private void cargarPosition() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Criteria criteria = new Criteria();
        String bestProvider = mLocationManager.getBestProvider(criteria, false);
        Location location = mLocationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            marcar = true;
            myLat = location.getLatitude();
            myLng = location.getLongitude();
            myLatLng = new LatLng(myLat, myLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, zoom));
        } else {
            marcar = false;
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                createLocationDialog();
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, fast ? FAST_GPS_UPDATE : SLOW_GPS_UPDATE, MIN_DISTANCE_GPS_UPDATES, this);
        }
        cargarCinemas();
    }


    @Override
    public boolean onMyLocationButtonClick() {
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            marcar = true;
            cargarPosition();
        }
        return false;
    }

    public void createLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¿Usar sistema de localización?")
                .setMessage("Para poder determinar tu ubicación y mostrarte información personalizada, es necesario que actives tu ubicación")
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                marcar = true;
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        builder.create();
        builder.show();
    }
}
