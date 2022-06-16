package com.yc.jsdsp.beans.module.beans;

import java.util.List;

public class HotWithDrawBeans {

    /**
     * huoli_num : 75
     * huoli_cash : 0.01
     * huoli : {"id":1,"txstatus":1,"tx_bili":10000,"seevido":0,"content":"","config_json":[{"money":"0.3","num":"1","is_check":"0","status":1,"finish_num":0}],"exchange_cash":"0.01"}
     */

    private int huoli_num;
    private String huoli_cash;
    private HuoliBean huoli;
    private QuestionHuoliBean question_huoli;

    public QuestionHuoliBean getQuestion_huoli() {
        return question_huoli;
    }

    public void setQuestion_huoli(QuestionHuoliBean question_huoli) {
        this.question_huoli = question_huoli;
    }

    public int getHuoli_num() {
        return huoli_num;
    }

    public void setHuoli_num(int huoli_num) {
        this.huoli_num = huoli_num;
    }

    public String getHuoli_cash() {
        return huoli_cash;
    }

    public void setHuoli_cash(String huoli_cash) {
        this.huoli_cash = huoli_cash;
    }

    public HuoliBean getHuoli() {
        return huoli;
    }

    public void setHuoli(HuoliBean huoli) {
        this.huoli = huoli;
    }



    public static class QuestionHuoliBean {
        /**
         * id : 1
         * agent_pid : 173
         * agent_id : 1
         * first_video : 1
         * video_num : 1
         * total : 10
         * ad_video : 1,2,3
         * award : 100
         * award_config : ["400","250","200","150"]
         * level : 1
         * level_state : 0
         * is_new_user : 0
         * shuoming : <p><span style="text-decoration: none;">点击开始任务，观看视频并点击下载安装即可获得最高百倍的活跃值奖励！</span></p>
         */

        private int id;
        private int agent_pid;
        private int agent_id;
        private int first_video;
        private int video_num;
        private int total;
        private String ad_video;
        private int award;
        private String award_config;
        private int level;
        private int level_state;
        private int is_new_user;
        private String shuoming;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAgent_pid() {
            return agent_pid;
        }

        public void setAgent_pid(int agent_pid) {
            this.agent_pid = agent_pid;
        }

        public int getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(int agent_id) {
            this.agent_id = agent_id;
        }

        public int getFirst_video() {
            return first_video;
        }

        public void setFirst_video(int first_video) {
            this.first_video = first_video;
        }

        public int getVideo_num() {
            return video_num;
        }

        public void setVideo_num(int video_num) {
            this.video_num = video_num;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getAd_video() {
            return ad_video;
        }

        public void setAd_video(String ad_video) {
            this.ad_video = ad_video;
        }

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public String getAward_config() {
            return award_config;
        }

        public void setAward_config(String award_config) {
            this.award_config = award_config;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLevel_state() {
            return level_state;
        }

        public void setLevel_state(int level_state) {
            this.level_state = level_state;
        }

        public int getIs_new_user() {
            return is_new_user;
        }

        public void setIs_new_user(int is_new_user) {
            this.is_new_user = is_new_user;
        }

        public String getShuoming() {
            return shuoming;
        }

        public void setShuoming(String shuoming) {
            this.shuoming = shuoming;
        }
    }


    public static class HuoliBean {
        /**
         * id : 1
         * txstatus : 1
         * tx_bili : 10000
         * seevido : 0
         * content :
         * config_json : [{"money":"0.3","num":"1","is_check":"0","status":1,"finish_num":0}]
         * exchange_cash : 0.01
         */

        private int id;
        private int txstatus;
        private int tx_bili;
        private int seevido;
        private String content;
        private String exchange_cash;
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

        public String getExchange_cash() {
            return exchange_cash;
        }

        public void setExchange_cash(String exchange_cash) {
            this.exchange_cash = exchange_cash;
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
             * num : 1
             * is_check : 0
             * status : 1
             * finish_num : 0
             */
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            private String money;
            private String num;
            private String is_check;
            private int status;
            private int finish_num;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getFinish_num() {
                return finish_num;
            }

            public void setFinish_num(int finish_num) {
                this.finish_num = finish_num;
            }
        }
    }
}
