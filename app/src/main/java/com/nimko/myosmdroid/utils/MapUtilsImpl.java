package com.nimko.myosmdroid.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.nimko.myosmdroid.R;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapUtilsImpl implements MapUtils{
    private final MapView map;
    private final Context context;

    private static final double DEGREE_RADIUS = 0.5;
    private static final String MY_USER_AGENT = System.getProperty("http.agent");
    private final static double START_ZOOM = 13.5;
    private static final GeoPoint DEFAULT_POINT = new GeoPoint(50.0,36.25);

    public MapUtilsImpl(MapView map, Context context) {
        Configuration.getInstance().load(context,
                context.getSharedPreferences("MyOsmDroid", Context.MODE_PRIVATE));
        map.setMultiTouchControls(true);
        map.getController().setZoom(START_ZOOM);
        map.getController().setCenter(DEFAULT_POINT);
        this.map = map;
        this.context = context;

    }

    @Override
    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void addMarker(GeoPoint point, MarkImg icon, String text) {
        Marker marker = new Marker(map);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setIcon(context.getDrawable(icon.getId()));
        if (text != null) marker.setTitle(text);
        map.getOverlays().add(marker);
        map.getController().setCenter(point);
    }

    @Override
    @SuppressLint("MissingPermission")
    public GeoPoint getMyStartLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return myLocation != null ?
                new GeoPoint(myLocation.getLatitude(),myLocation.getLongitude()):
                DEFAULT_POINT;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMyLocation() {
        getLocation().observeOn(Schedulers.computation())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(myLocationNewOverlay -> {
                    map.getOverlays().add(myLocationNewOverlay);
                    map.invalidate();
                }).subscribe();
    }

    @SuppressLint({"CheckResult"})
    @Override
    public void buildRout(GeoPoint start, GeoPoint end) {
        getLine(start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(s -> {
                    addMarker(getMyStartLocation(),MarkImg.START, context.getString(R.string.start));
                    DecimalFormat numForm = new DecimalFormat("#.##");
                    addMarker(end, MarkImg.FINISH,
                            context.getString(R.string.distance)
                                    + numForm.format(s.getDistance()/1000)
                                    + context.getString(R.string.km));
                    map.getOverlays().add(s);
                    map.invalidate();
                }).subscribe();
        getMyLocation();
    }

    @Override
    public void deleteLastRout() {
        map.getOverlays().clear();
    }


    private Single<Polyline> getLine(GeoPoint start, GeoPoint end){
        return Single.create(emitter -> {
            ArrayList<GeoPoint> waypoints = new ArrayList<>();
            waypoints.add(start);
            waypoints.add(end);
            RoadManager roadManager = new OSRMRoadManager(context, MY_USER_AGENT);
            Road road = roadManager.getRoad(waypoints);
            emitter.onSuccess(RoadManager.buildRoadOverlay(road));
        });
    }

    public static GeoPoint getRandomPoint(GeoPoint myPoint){
        return new GeoPoint(
                getRandomValue(myPoint.getLatitude()),
                getRandomValue(myPoint.getLongitude()));
    }

    private static Double getRandomValue(double value){
        Random random = new Random();
        if (random.nextBoolean())
            value += random.nextGaussian() * MapUtilsImpl.DEGREE_RADIUS;
        else
            value -= random.nextGaussian() * MapUtilsImpl.DEGREE_RADIUS;
        return value;
    }

    private Single<MyLocationNewOverlay> getLocation(){
        return Single.create(emitter -> {
            MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),map);
            mLocationOverlay.enableMyLocation();
            emitter.onSuccess(mLocationOverlay);
        });
    }
}
