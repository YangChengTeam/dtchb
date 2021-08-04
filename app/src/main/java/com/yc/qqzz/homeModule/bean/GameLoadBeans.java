package com.yc.qqzz.homeModule.bean;

public class GameLoadBeans {
    private String downUrl;
    private String desc;
    private String speed;

    private long totalSize;
    private long offsetSize;

    private boolean isDownloading;
    private int downloadStatus;

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getOffsetSize() {
        return offsetSize;
    }

    public void setOffsetSize(long offsetSize) {
        this.offsetSize = offsetSize;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
