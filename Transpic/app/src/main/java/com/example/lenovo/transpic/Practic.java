package com.example.lenovo.transpic;

/**
 * Created by lenovo on 2018/4/10.
 */

public class Practic {

    private String name;
    private int imageId;
    private String SID;

    public Practic(String name, int imageId,String SID) {
        this.name = name;
        this.imageId = imageId;
        this.SID = SID;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getSID() {
        return SID;
    }
}
