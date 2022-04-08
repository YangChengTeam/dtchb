package com.yc.wxchb.beans.module.beans;

import java.util.List;

public class RedWallInfoBeans {

    /**
     * cash : 24.94
     * cash_gold : {"id":1,"txstatus":1,"tx_bili":10000,"ad_code":"1","gold_range":"20100","seevido":0,"content":"1","config_json":[{"money":"0.3","num":"3000","is_check":"0"}]}
     * gold_cash : 0
     * gold_num : 0
     */

    private String cash;
    private CashGoldBean cash_gold;
    private String gold_cash;
    private int gold_num;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public CashGoldBean getCash_gold() {
        return cash_gold;
    }

    public void setCash_gold(CashGoldBean cash_gold) {
        this.cash_gold = cash_gold;
    }

    public String getGold_cash() {
        return gold_cash;
    }

    public void setGold_cash(String gold_cash) {
        this.gold_cash = gold_cash;
    }

    public int getGold_num() {
        return gold_num;
    }

    public void setGold_num(int gold_num) {
        this.gold_num = gold_num;
    }

    public static class CashGoldBean {
        /**
         * id : 1
         * txstatus : 1
         * tx_bili : 10000
         * ad_code : 1
         * gold_range : 20100
         * seevido : 0
         * content : 1
         * config_json : [{"money":"0.3","num":"3000","is_check":"0"}]
         */

        private int id;
        private int txstatus;
        private int tx_bili;
        private String ad_code;
        private String gold_range;
        private int seevido;
        private String content;
        private List<ConfigJsonBean> config_json;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTxstatus() {
            return txstatus;
        }

        public void setTxstatus(int txstatus) {
            this.txstatus = txstatus;
        }

        public int getTx_bili() {
            return tx_bili;
        }

        public void setTx_bili(int tx_bili) {
            this.tx_bili = tx_bili;
        }

        public String getAd_code() {
            return ad_code;
        }

        public void setAd_code(String ad_code) {
            this.ad_code = ad_code;
        }

        public String getGold_range() {
            return gold_range;
        }

        public void setGold_range(String gold_range) {
            this.gold_range = gold_range;
        }

        public int getSeevido() {
            return seevido;
        }

        public void setSeevido(int seevido) {
            this.seevido = seevido;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ConfigJsonBean> getConfig_json() {
            return config_json;
        }

        public void setConfig_json(List<ConfigJsonBean> config_json) {
            this.config_json = config_json;
        }

        public static class ConfigJsonBean {
            /**
             * money : 0.3
             * num : 3000
             * is_check : 0
             */
            private boolean isSelect;
            private String money;
            private String num;
            private String is_check;
            private String daynum;
            private int finish_num;

            public String getDaynum() {
                return daynum;
            }

            public void setDaynum(String daynum) {
                this.daynum = daynum;
            }

            public int getFinish_num() {
                return finish_num;
            }

            public void setFinish_num(int finish_num) {
                this.finish_num = finish_num;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getIs_check() {
                return is_check;
            }

            public void setIs_check(String is_check) {
                this.is_check = is_check;
            }
        }
    }
}
