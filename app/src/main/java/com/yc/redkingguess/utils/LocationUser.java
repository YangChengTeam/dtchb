package com.yc.redkingguess.utils;



public class LocationUser {
    private static volatile LocationUser instance;
    public  String city;
    public static LocationUser getInstance() {
        if (instance == null) {
            synchronized (LocationUser.class) {
                if (instance == null) {
                    instance = new LocationUser();
                }
            }
        }
        return instance;
    }
    public void setLocation(String city){
        this.city=city;
    }
    public String getLocation(){
        return city;
    }

}
