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

        private String cash;
        private int level;
        public CashEvent(String allMoneys,String money, String cash,int level) {
            this.allMoneys=allMoneys;
            this.moneys=money;
            this.cash=cash;
            this.level=level;
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

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    public static class CashTipsEvent extends Event{
        public CashTipsEvent() {

        }
    }
    public static class LineRedEvent extends Event{
        public LineRedEvent() {

        }
    }
    public static class LineRedEventTwo extends Event{
        public LineRedEventTwo() {

        }
    }

    public static class HomeRed extends Event{
        public HomeRed() {

        }
    }
}
