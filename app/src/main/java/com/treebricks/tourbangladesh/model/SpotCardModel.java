package com.treebricks.tourbangladesh.model;

/**
 * Created by fahim on 11/7/16.
 */

public class SpotCardModel
{
    private String spotImageUrl;
    private String spotName;
    private String spotLocation;
    private String spotDistance;

    public SpotCardModel(String spotImageUrl, String spotName, String spotLocation, String spotDistance) {
        this.spotImageUrl = spotImageUrl;
        this.spotName = spotName;
        this.spotLocation = spotLocation;
        this.spotDistance = spotDistance;
    }

    public String getSpotImageUrl() {
        return spotImageUrl;
    }

    public void setSpotImageUrl(String spotImageUrl) {
        this.spotImageUrl = spotImageUrl;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotLocation() {
        return spotLocation;
    }

    public void setSpotLocation(String spotLocation) {
        this.spotLocation = spotLocation;
    }

    public String getSpotDistance() {
        return spotDistance;
    }

    public void setSpotDistance(String spotDistance) {
        this.spotDistance = spotDistance;
    }
}
