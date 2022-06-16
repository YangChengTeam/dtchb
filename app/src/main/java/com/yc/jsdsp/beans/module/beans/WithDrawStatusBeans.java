package com.yc.jsdsp.beans.module.beans;

public class WithDrawStatusBeans {

    /**
     * status : 1
     * err_msg :
     */

    private int status;
    private String err_msg;
    private String cash;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
}
