package com.yc.redkingguess.homeModule.module.bean;

import java.io.Serializable;
import java.util.List;

public class InvationsShareBeans implements Serializable {


    /**
     * invite_num : 10
     * exchange_num : 1
     * invite_list : [{"id":1,"level_upgrade":4,"level_exchange":"2,3","cash_exchange":"0.3","exchange_num":1,"is_check":1}]
     */

    private int invite_num;
    private int exchange_num;
    private String tx_wxid;
    private  int user_level;

    public String getTx_wxid() {
        return tx_wxid;
    }

    public void setTx_wxid(String tx_wxid) {
        this.tx_wxid = tx_wxid;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    private List<InviteListBean> invite_list;

    public int getInvite_num() {
        return invite_num;
    }

    public void setInvite_num(int invite_num) {
        this.invite_num = invite_num;
    }

    public int getExchange_num() {
        return exchange_num;
    }

    public void setExchange_num(int exchange_num) {
        this.exchange_num = exchange_num;
    }

    public List<InviteListBean> getInvite_list() {
        return invite_list;
    }

    public void setInvite_list(List<InviteListBean> invite_list) {
        this.invite_list = invite_list;
    }

    public static class InviteListBean implements Serializable {
        /**
         * id : 1
         * level_upgrade : 4
         * level_exchange : 2,3
         * cash_exchange : 0.3
         * exchange_num : 1
         * is_check : 1
         */
        private boolean isSelect;
        private int id;
        private int level_upgrade;
        private String level_exchange;
        private String cash_exchange;
        private int exchange_num;
        private int is_check;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel_upgrade() {
            return level_upgrade;
        }

        public void setLevel_upgrade(int level_upgrade) {
            this.level_upgrade = level_upgrade;
        }

        public String getLevel_exchange() {
            return level_exchange;
        }

        public void setLevel_exchange(String level_exchange) {
            this.level_exchange = level_exchange;
        }

        public String getCash_exchange() {
            return cash_exchange;
        }

        public void setCash_exchange(String cash_exchange) {
            this.cash_exchange = cash_exchange;
        }

        public int getExchange_num() {
            return exchange_num;
        }

        public void setExchange_num(int exchange_num) {
            this.exchange_num = exchange_num;
        }

        public int getIs_check() {
            return is_check;
        }

        public void setIs_check(int is_check) {
            this.is_check = is_check;
        }
    }
}
