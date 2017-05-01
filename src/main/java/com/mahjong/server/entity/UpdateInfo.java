package com.mahjong.server.entity;

public class UpdateInfo {
    private Integer id;

    private Byte deviceType;

    private Float appVersion;

    private String downUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public Float getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Float appVersion) {
        this.appVersion = appVersion;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl == null ? null : downUrl.trim();
    }
}