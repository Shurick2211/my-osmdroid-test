package com.nimko.myosmdroid.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapUtilsImpl implements MapUtils{
    private MapView map;
    private Context context;
    private int idRout = 0;
    private static final String MY_USER_AGENT = System.getProperty("http.agent");
    private final static double START_ZOOM = 13.5;
    private static final GeoPoint DEFAULT_POINT = new GeoPoint(50.0,36.25);

    public MapUtilsImpl(MapView map, Context context) {
        Configuration.getInstance().load(context,
                PreferenceManager.getDefaultSharedPreferences(context));
        map.setMultiTouchControls(true);
        map.getController().setZoom(START_ZOOM);
        this.map = map;
        this.context = context;

    }

    @Override
    public void addMarker(GeoPoint point) {
        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        map.getOverlays().add(startMarker);
        map.getController().setCenter(point);
    }

    @Override
    @SuppressLint("MissingPermission")
    public GeoPoint getMyLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return myLocation != null ?
                new GeoPoint(myLocation.getLatitude(),myLocation.getLongitude()):
                DEFAULT_POINT;
    }

    @SuppressLint("CheckResult")
    @Override
    public void buildRout(GeoPoint start, GeoPoint end) {
        getLine(start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    addMarker(end);
                    map.getOverlays().add(s);
                    idRout = map.getOverlays().size() - 1;
                    map.invalidate();
                });
    }

    @Override
    public void deleteLastRout() {
        map.getOverlays().clear();
        addMarker(getMyLocation());
        map.invalidate();
    }


    private Single<Polyline> getLine(GeoPoint start, GeoPoint end){
        return Single.create(new SingleOnSubscribe<Polyline>() {
            @Override
            public void subscribe(SingleEmitter<Polyline> emitter) throws Exception {
                ArrayList<GeoPoint> waypoints = new ArrayList<>();
                waypoints.add(start);
                waypoints.add(end);
                RoadManager roadManager = new OSRMRoadManager(context, MY_USER_AGENT);
                Road road = roadManager.getRoad(waypoints);
                emitter.onSuccess(RoadManager.buildRoadOverlay(road));
            }
        });
    }
}
