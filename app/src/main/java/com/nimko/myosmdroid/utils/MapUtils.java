package com.nimko.myosmdroid.utils;

import org.osmdroid.util.GeoPoint;

public interface MapUtils {
    void addMarker(GeoPoint point);
    GeoPoint getMyLocation();
    void buildRout(GeoPoint start, GeoPoint end);
    void deleteLastRout();
}
