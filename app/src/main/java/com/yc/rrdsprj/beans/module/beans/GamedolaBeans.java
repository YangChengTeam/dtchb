package com.yc.rrdsprj.beans.module.beans;

public class GamedolaBeans {

    /**
     * game_total : 1
     * total : 1
     * game_info : {"id":1,"user_id":7049,"status":0,"huoli":100,"add_time":1650768616,"add_date":20220424}
     */

    private int game_total;
    private int total;
    private GameInfoBeans game_info;

    public int getGame_total() {
        return game_total;
    }

    public void setGame_total(int game_total) {
        this.game_total = game_total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public GameInfoBeans getGame_info() {
        return game_info;
    }

    public void setGame_info(GameInfoBeans game_info) {
        this.game_info = game_info;
    }
}
