package com.yc.jsdps.beans.module.beans;


import java.util.List;

public class AdCodeBeans {


    /**
     * ad_banner : 946546925
     * ad_jili : 946549271
     * ad_express : 946546912
     * ad_insert : 946547020
     * ad_kaiping : 887539922
     * ad_tx_jili : 6092225141129875
     * server_ip : 119.23.173.220
     * res_city : 火星
     * ip : 117.136.81.192
     * app_toast : 1
     * agent_login : {"id":1,"agent_id":"1","agent_ads":"","app_type":2,"share_img":"http://qqzz.x6h.com/uploads/agentlogin/20210810/3236e466561f4852dcc2866a74ed1ad6.png","share_url":"https://www.x6h.com/qingquzz/","cash_status":1}
     * ad_config : {"jili":{"csj":{"num":"3","sort":"2"},"tx":{"num":"2","sort":"1"}}}
     * ad_gromore : [{"position":"ad_banner","code":"0"},{"position":"ad_jili","code":"0"},{"position":"ad_express","code":"0"},{"position":"ad_insert","code":"0"},{"position":"ad_kaiping","code":"0"}]
     */
    private String ad_ks_jili;
    private String ad_ks_insert;
    private String ad_tx_insert;
    private String ad_tx_express;
    private String ad_banner;
    private String ad_jili;
    private String ad_express;
    private String ad_insert;
    private String ad_kaiping;
    private String ad_tx_jili;
    private String server_ip;
    private String res_city;
    private String ip;
    private int app_toast;
    private String cash_gold_ad;
    private AgentLoginBean agent_login;
    private AdConfigBean ad_config;
    private AdConfigBean download_ad;
    private List<AdGromoreBean> ad_gromore;
    private List<AdReportBean> ad_report;
    private List<AdInfoBeans> ad_csj;
    private List<AdInfoBeans> ad_ks;
    private List<AdInfoBeans> ad_tx;

    public String getCash_gold_ad() {
        return cash_gold_ad;
    }

    public void setCash_gold_ad(String cash_gold_ad) {
        this.cash_gold_ad = cash_gold_ad;
    }

    public AdConfigBean getDownload_ad() {
        return download_ad;
    }

    public void setDownload_ad(AdConfigBean download_ad) {
        this.download_ad = download_ad;
    }

    public List<AdInfoBeans> getAd_csj() {
        return ad_csj;
    }

    public void setAd_csj(List<AdInfoBeans> ad_csj) {
        this.ad_csj = ad_csj;
    }

    public List<AdInfoBeans> getAd_ks() {
        return ad_ks;
    }

    public void setAd_ks(List<AdInfoBeans> ad_ks) {
        this.ad_ks = ad_ks;
    }

    public List<AdInfoBeans> getAd_tx() {
        return ad_tx;
    }

    public void setAd_tx(List<AdInfoBeans> ad_tx) {
        this.ad_tx = ad_tx;
    }

    private int is_pb;

    public int getIs_pb() {
        return is_pb;
    }

    public void setIs_pb(int is_pb) {
        this.is_pb = is_pb;
    }

    public String getAd_ks_jili() {
        return ad_ks_jili;
    }

    public void setAd_ks_jili(String ad_ks_jili) {
        this.ad_ks_jili = ad_ks_jili;
    }

    public String getAd_ks_insert() {
        return ad_ks_insert;
    }

    public void setAd_ks_insert(String ad_ks_insert) {
        this.ad_ks_insert = ad_ks_insert;
    }

    public String getAd_tx_insert() {
        return ad_tx_insert;
    }

    public void setAd_tx_insert(String ad_tx_insert) {
        this.ad_tx_insert = ad_tx_insert;
    }

    public String getAd_tx_express() {
        return ad_tx_express;
    }

    public void setAd_tx_express(String ad_tx_express) {
        this.ad_tx_express = ad_tx_express;
    }

    public List<AdReportBean> getAd_report() {
        return ad_report;
    }

    public void setAd_report(List<AdReportBean> ad_report) {
        this.ad_report = ad_report;
    }

    public String getAd_banner() {
        return ad_banner;
    }

    public void setAd_banner(String ad_banner) {
        this.ad_banner = ad_banner;
    }

    public String getAd_jili() {
        return ad_jili;
    }

    public void setAd_jili(String ad_jili) {
        this.ad_jili = ad_jili;
    }

    public String getAd_express() {
        return ad_express;
    }

    public void setAd_express(String ad_express) {
        this.ad_express = ad_express;
    }

    public String getAd_insert() {
        return ad_insert;
    }

    public void setAd_insert(String ad_insert) {
        this.ad_insert = ad_insert;
    }

    public String getAd_kaiping() {
        return ad_kaiping;
    }

    public void setAd_kaiping(String ad_kaiping) {
        this.ad_kaiping = ad_kaiping;
    }

    public String getAd_tx_jili() {
        return ad_tx_jili;
    }

    public void setAd_tx_jili(String ad_tx_jili) {
        this.ad_tx_jili = ad_tx_jili;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getRes_city() {
        return res_city;
    }

    public void setRes_city(String res_city) {
        this.res_city = res_city;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getApp_toast() {
        return app_toast;
    }

    public void setApp_toast(int app_toast) {
        this.app_toast = app_toast;
    }

    public AgentLoginBean getAgent_login() {
        return agent_login;
    }

    public void setAgent_login(AgentLoginBean agent_login) {
        this.agent_login = agent_login;
    }

    public AdConfigBean getAd_config() {
        return ad_config;
    }

    public void setAd_config(AdConfigBean ad_config) {
        this.ad_config = ad_config;
    }

    public List<AdGromoreBean> getAd_gromore() {
        return ad_gromore;
    }

    public void setAd_gromore(List<AdGromoreBean> ad_gromore) {
        this.ad_gromore = ad_gromore;
    }

    public static class AgentLoginBean {
        /**
         * id : 1
         * agent_id : 1
         * agent_ads :
         * app_type : 2
         * share_img : http://qqzz.x6h.com/uploads/agentlogin/20210810/3236e466561f4852dcc2866a74ed1ad6.png
         * share_url : https://www.x6h.com/qingquzz/
         * cash_status : 1
         */
        private int kaiping;
        private int video_cash;
        private int id;
        private String agent_id;
        private String agent_ads;
        private int app_type;
        private String share_img;
        private String share_url;
        private int cash_status;
        private int hb_ad_type;
        private int ad_follow;
        private int video_change;
        private String shield_agent;
        private String express_agent;
        private String game_agent;

        public String getGame_agent() {
            return game_agent;
        }

        public void setGame_agent(String game_agent) {
            this.game_agent = game_agent;
        }

        public String getExpress_agent() {
            return express_agent;
        }

        public void setExpress_agent(String express_agent) {
            this.express_agent = express_agent;
        }

        public String getShield_agent() {
            return shield_agent;
        }

        public void setShield_agent(String shield_agent) {
            this.shield_agent = shield_agent;
        }

        public int getVideo_change() {
            return video_change;
        }

        public void setVideo_change(int video_change) {
            this.video_change = video_change;
        }
        public int getAd_follow() {
            return ad_follow;
        }

        public void setAd_follow(int ad_follow) {
            this.ad_follow = ad_follow;
        }

        public int getHb_ad_type() {
            return hb_ad_type;
        }

        public void setHb_ad_type(int hb_ad_type) {
            this.hb_ad_type = hb_ad_type;
        }

        public int getVideo_cash() {
            return video_cash;
        }

        public void setVideo_cash(int video_cash) {
            this.video_cash = video_cash;
        }

        public int getKaiping() {
            return kaiping;
        }

        public void setKaiping(int kaiping) {
            this.kaiping = kaiping;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getAgent_ads() {
            return agent_ads;
        }

        public void setAgent_ads(String agent_ads) {
            this.agent_ads = agent_ads;
        }

        public int getApp_type() {
            return app_type;
        }

        public void setApp_type(int app_type) {
            this.app_type = app_type;
        }

        public String getShare_img() {
            return share_img;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getCash_status() {
            return cash_status;
        }

        public void setCash_status(int cash_status) {
            this.cash_status = cash_status;
        }
    }



    public static class AdConfigBean {
        /**
         * jili : {"csj":{"num":"3","sort":"2"},"tx":{"num":"2","sort":"1"}}
         */

        private JiliBean jili;

        public JiliBean getJili() {
            return jili;
        }

        public void setJili(JiliBean jili) {
            this.jili = jili;
        }
        private JiliBean chaping;

        public JiliBean getChaping() {
            return chaping;
        }

        public void setChaping(JiliBean chaping) {
            this.chaping = chaping;
        }
        public static class JiliBean {
            /**
             * csj : {"num":"3","sort":"2"}
             * tx : {"num":"2","sort":"1"}
             */

            private CsjBean csj;
            private TxBean tx;
            private KsBean ks;

            public KsBean getKs() {
                return ks;
            }

            public void setKs(KsBean ks) {
                this.ks = ks;
            }

            public CsjBean getCsj() {
                return csj;
            }

            public void setCsj(CsjBean csj) {
                this.csj = csj;
            }

            public TxBean getTx() {
                return tx;
            }

            public void setTx(TxBean tx) {
                this.tx = tx;
            }

            public static class CsjBean {
                /**
                 * num : 3
                 * sort : 2
                 */
                private String ad_code;
                private String num;
                private String sort;

                public String getAd_code() {
                    return ad_code;
                }

                public void setAd_code(String ad_code) {
                    this.ad_code = ad_code;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }
            }
            public static class KsBean {
                /**
                 * num : 3
                 * sort : 2
                 */

                private String num;
                private String sort;
                private String ad_code;

                public String getAd_code() {
                    return ad_code;
                }

                public void setAd_code(String ad_code) {
                    this.ad_code = ad_code;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }
            }
            public static class TxBean {
                /**
                 * num : 2
                 * sort : 1
                 */
                private String ad_code;
                private String num;
                private String sort;

                public String getAd_code() {
                    return ad_code;
                }

                public void setAd_code(String ad_code) {
                    this.ad_code = ad_code;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }
            }
        }
    }

    public static class AdGromoreBean {
        /**
         * position : ad_banner
         * code : 0
         */

        private String position;
        private String code;
        private String high_code;

        public String getHigh_code() {
            return high_code;
        }

        public void setHigh_code(String high_code) {
            this.high_code = high_code;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    public static class AdReportBean {
        /**
         * id : 1
         * agent_pid : 170
         * state : 1
         * agent_ids :
         */

        private int id;
        private int agent_pid;
        private int state;
        private String agent_ids;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAgent_ids() {
            return agent_ids;
        }

        public void setAgent_ids(String agent_ids) {
            this.agent_ids = agent_ids;
        }
    }
}
