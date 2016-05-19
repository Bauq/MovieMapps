package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Cinema;

/**
 * Created by Brian on 05/05/2016.
 */
public class MapaCinemasFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    public static final int ID = 8;

    private static final int FAST_GPS_UPDATE = 3000;
    private static final int SLOW_GPS_UPDATE = 30000;
    private static final int MIN_DISTANCE_GPS_UPDATES = 0;
    private static boolean fast = false;
    private static float zoom = (float) 15.5;
    private static DecimalFormat twoDForm = new DecimalFormat("#.##");

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private Double myLat, myLng;
    private LatLng myLatLng;
    private boolean marcar = true;

    private List<Cinema> cinemaList = new ArrayList<>();
    private static View rootView;
    Cinema cineCercano, selected;
    double menorDistancia = Double.MAX_VALUE;

    private static int idCinema;

    public static MapaCinemasFragment newInstance(int idCinema) {

        //Este id Cinema se utiliza cuando de otro lugar voy a pedir que me abra el mapa enfocando
        //un cinema especifico
        MapaCinemasFragment.idCinema = idCinema;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
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

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void cargarCinemas() {
        Log.e("Prueba: ", "Cargar Cinemas");
        List<Cinema> listaCinemas = new ArrayList<>();
        Cinema c = new Cinema();
        c.setNombre("Cine Colombia Los Molinos");
        c.setLatitud(6.232321400000001);
        c.setLongitud(-75.60410279999996);
        listaCinemas.add(c);
        c = new Cinema();
        c.setNombre("Procinal Puerta del norte");
        c.setLatitud((double) 3);
        c.setLongitud((double) -74);
        listaCinemas.add(c);
        c = new Cinema();
        c.setNombre("Procinal Santa fe");
        c.setLatitud((double) 23);
        c.setLongitud((double) 345346546);
        listaCinemas.add(c);
        c = new Cinema();
        c.setNombre("Royal Films Bosque Plaza");
        c.setLatitud((double) 6.268674619223301);
        c.setLongitud((double) -75.56475877761841);
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
            LatLngCine = new LatLng(cine.getLatitud(), cine.getLongitud());

            d = distancia(myLat, myLng, cinemaList.get(i).getLatitud(), cinemaList.get(i).getLongitud());
            if (menorDistancia > d) {
                menorDistancia = d;
                cineCercano = cinemaList.get(i);
            }
            if (d < 1) {
                d = d * 1000;
                unidad = " m";
            } else {
                unidad = " km";
            }
            mMap.addMarker(new MarkerOptions()
                    .position(LatLngCine)
                    .title(cine.getNombre())
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
                createSimpleDialog();
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, fast ? FAST_GPS_UPDATE : SLOW_GPS_UPDATE, MIN_DISTANCE_GPS_UPDATES, this);
        }
        cargarCinemas();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int i = Integer.parseInt(marker.getId().substring(1));
        selected = cinemaList.get(i);
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            marcar = true;
            cargarPosition();
        }
        return false;
    }

    public void createSimpleDialog() {
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
                                //mListener.setFragment(RecetasSugeridasFragment.ID, null, false);
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //mListener.setFragment(RecetasSugeridasFragment.ID, null, false);
                            }
                        });

        builder.create();
        builder.show();
    }
}
