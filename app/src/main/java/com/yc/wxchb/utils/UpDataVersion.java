package com.yc.wxchb.utils;

public class UpDataVersion {


    /**
     * id : 1
     * agent_id : 0
     * version_name : 2
     * version_code : 34
     * update_content :
     * force_update : 1
     * download_url :
     */

    private int id;
    private String down_url;
    private String app_name;
    private String icon_url;
    private String package_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
