package com.treebricks.tourbangladesh.model;

/**
 * Created by fahim on 11/7/16.
 */

public class SpotModel
{
    private String spotId;
    private String spotName;
    private String spotCatagory;
    private String spotLocation;
    private String spotDistrict;
    private String spotDescription;
    private String latitude;
    private String longitude;
    private String spotImageUrl;

    public SpotModel() {
    }

    public SpotModel(String spotId, String spotName, String spotCatagory,
                     String spotLocation, String spotDistrict, String spotDescription,
                     String latitude, String longitude, String spotImageUrl) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.spotCatagory = spotCatagory;
        this.spotLocation = spotLocation;
        this.spotDistrict = spotDistrict;
        this.spotDescription = spotDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spotImageUrl = spotImageUrl;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotCatagory() {
        return spotCatagory;
    }

    public void setSpotCatagory(String spotCatagory) {
        this.spotCatagory = spotCatagory;
    }

    public String getSpotLocation() {
        return spotLocation;
    }

    public void setSpotLocation(String spotLocation) {
        this.spotLocation = spotLocation;
    }

    public String getSpotDistrict() {
        return spotDistrict;
    }

    public void setSpotDistrict(String spotDistrict) {
        this.spotDistrict = spotDistrict;
    }

    public String getSpotDescription() {
        return spotDescription;
    }

    public void setSpotDescription(String spotDescription) {
        this.spotDescription = spotDescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSpotImageUrl() {
        return spotImageUrl;
    }

    public void setSpotImageUrl(String spotImageUrl) {
        this.spotImageUrl = spotImageUrl;
    }
}
