package com.yc.wxchb.service.event;


public class Event {
    public static class LoginEvent extends Event{
        String face;
        public LoginEvent(String face) {
            this.face=face;
        }
        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }



    public static class HotVideoEvent extends Event{
        public HotVideoEvent() {

        }
    }



}
