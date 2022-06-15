package com.yc.jsdps.beans.module.beans;

import java.util.List;

public class InvitationShareBeans {

    /**
     * invite_config : {"id":1,"agent_ids":"1,2","content":"","exchange_day":1,"is_open":1}
     * invite_today : 0
     * invite_code : I50dFK
     * be_invite_code :
     * invite_num : 10
     * invite_meet_num : 5
     * invite_exchange_num : 4
     * invite_list : [{"id":1,"cash_exchange":"0.3","exchange_num":1,"is_check":1}]
     */

    private InviteConfigBean invite_config;
    private int invite_today;
    private String invite_code;
    private String be_invite_code;
    private int invite_num;
    private int invite_meet_num;
    private int invite_exchange_num;
    private List<InviteListBean> invite_list;

    public InviteConfigBean getInvite_config() {
        return invite_config;
    }

    public void setInvite_config(InviteConfigBean invite_config) {
        this.invite_config = invite_config;
    }

    public int getInvite_today() {
        return invite_today;
    }

    public void setInvite_today(int invite_today) {
        this.invite_today = invite_today;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getBe_invite_code() {
        return be_invite_code;
    }

    public void setBe_invite_code(String be_invite_code) {
        this.be_invite_code = be_invite_code;
    }

    public int getInvite_num() {
        return invite_num;
    }

    public void setInvite_num(int invite_num) {
        this.invite_num = invite_num;
    }

    public int getInvite_meet_num() {
        return invite_meet_num;
    }

    public void setInvite_meet_num(int invite_meet_num) {
        this.invite_meet_num = invite_meet_num;
    }

    public int getInvite_exchange_num() {
        return invite_exchange_num;
    }

    public void setInvite_exchange_num(int invite_exchange_num) {
        this.invite_exchange_num = invite_exchange_num;
    }

    public List<InviteListBean> getInvite_list() {
        return invite_list;
    }

    public void setInvite_list(List<InviteListBean> invite_list) {
        this.invite_list = invite_list;
    }

    public static class InviteConfigBean {
        /**
         * id : 1
         * agent_ids : 1,2
         * content :
         * exchange_day : 1
         * is_open : 1
         */

        private int id;
        private String agent_ids;
        private String content;
        private int exchange_day;
        private int is_open;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAgent_ids() {
            return agent_ids;
        }

        public void setAgent_ids(String agent_ids) {
            this.agent_ids = agent_ids;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getExchange_day() {
            return exchange_day;
        }

        public void setExchange_day(int exchange_day) {
            this.exchange_day = exchange_day;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }
    }

    public static class InviteListBean {
        /**
         * id : 1
         * cash_exchange : 0.3
         * exchange_num : 1
         * is_check : 1
         */
        private boolean isSelect;
        private int id;
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
