package com.github.cielsk.cityinfoparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class AddressInfo {

    @SerializedName("id") @Expose private String id;
    @SerializedName("cityEn") @Expose private String cityEn;
    @SerializedName("cityZh") @Expose private String cityZh;
    @SerializedName("countryCode") @Expose private String countryCode;
    @SerializedName("countryEn") @Expose private String countryEn;
    @SerializedName("countryZh") @Expose private String countryZh;
    @SerializedName("provinceEn") @Expose private String provinceEn;
    @SerializedName("provinceZh") @Expose private String provinceZh;
    @SerializedName("leaderEn") @Expose private String leaderEn;
    @SerializedName("leaderZh") @Expose private String leaderZh;
    @SerializedName("lat") @Expose private String lat;
    @SerializedName("lon") @Expose private String lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryZh() {
        return countryZh;
    }

    public void setCountryZh(String countryZh) {
        this.countryZh = countryZh;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getProvinceZh() {
        return provinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        this.provinceZh = provinceZh;
    }

    public String getLeaderEn() {
        return leaderEn;
    }

    public void setLeaderEn(String leaderEn) {
        this.leaderEn = leaderEn;
    }

    public String getLeaderZh() {
        return leaderZh;
    }

    public void setLeaderZh(String leaderZh) {
        this.leaderZh = leaderZh;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
