package com.yc.rrdsprj.beans.module.beans;

public class AdTypeBeans implements Comparable<AdTypeBeans>{
    private int sort;
    private int nums;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    @Override
    public int compareTo(AdTypeBeans o) {
        return this.sort-o.sort;
    }
}
