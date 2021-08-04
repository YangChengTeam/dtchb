package com.yc.qqzz.service.event;


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
    public static class LoadApkEvent extends Event{
        private int postion;
        private String moneys;
        public LoadApkEvent(int postions,String money) {
            this.postion=postions;
            this.moneys=money;
        }
        public int getPostion() {
            return postion;
        }

        public void setPostion(int postion) {
            this.postion = postion;
        }

        public String getMoneys() {
            return moneys;
        }

        public void setMoneys(String moneys) {
            this.moneys = moneys;
        }
    }
    public static class CashEvent extends Event{
        private String moneys;
        private String allMoneys;
        public CashEvent(String allMoneys,String money) {
            this.allMoneys=allMoneys;
            this.moneys=money;
        }

        public String getMoneys() {
            return moneys;
        }

        public void setMoneys(String moneys) {
            this.moneys = moneys;
        }

        public String getAllMoneys() {
            return allMoneys;
        }

        public void setAllMoneys(String allMoneys) {
            this.allMoneys = allMoneys;
        }
    }
}
