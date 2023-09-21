package com.nimko.myosmdroid.utils;

public enum MarkImg {
    START(org.osmdroid.bonuspack.R.drawable.osm_ic_follow_me_on),
    FINISH(org.osmdroid.bonuspack.R.drawable.osm_ic_follow_me),
    I_AM(org.osmdroid.bonuspack.R.drawable.person),
    DEFAULT(org.osmdroid.bonuspack.R.drawable.marker_default);

    private final int id;

    public int getId() {
        return id;
    }

    MarkImg(int id) {
        this.id = id;
    }
}
