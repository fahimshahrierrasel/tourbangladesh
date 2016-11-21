package com.treebricks.tourbangladesh.model;

/**
 * Created by fahim on 11/22/16.
 */

public class EmergencyModel
{
    private String providerName;
    private String providerLocatino;
    private Double providerLatitude;
    private Double providerLongitude;

    public EmergencyModel() {
    }

    public EmergencyModel(String providerName, String providerLocatino, Double providerLatitude, Double providerLongitude) {
        this.providerName = providerName;
        this.providerLocatino = providerLocatino;
        this.providerLatitude = providerLatitude;
        this.providerLongitude = providerLongitude;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderLocatino() {
        return providerLocatino;
    }

    public void setProviderLocatino(String providerLocatino) {
        this.providerLocatino = providerLocatino;
    }

    public Double getProviderLatitude() {
        return providerLatitude;
    }

    public void setProviderLatitude(Double providerLatitude) {
        this.providerLatitude = providerLatitude;
    }

    public Double getProviderLongitude() {
        return providerLongitude;
    }

    public void setProviderLongitude(Double providerLongitude) {
        this.providerLongitude = providerLongitude;
    }
}
