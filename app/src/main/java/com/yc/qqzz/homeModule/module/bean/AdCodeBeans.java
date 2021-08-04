package com.yc.qqzz.homeModule.module.bean;


public class AdCodeBeans {


    /**
     * ad_banner :
     * ad_jili :
     * ad_express :
     * ad_insert :
     * ad_kaiping :
     * server_ip : 120.25.238.32
     * app_toast : 0
     * agent_login : {"id":1,"agent_id":"1","agent_ads":"","app_type":1,"share_img":"","share_url":"","shield_area":"","bd_ak":""}
     * ad_config : {"jili":{"csj":{"num":"5","sort":"2"},"tx":{"num":"4","sort":"1"}}}
     */

    private String ad_banner;
    private String ad_jili;
    private String ad_express;
    private String ad_insert;
    private String ad_kaiping;
    private String server_ip;
    private String ad_tx_jili;
    private int app_toast;
    private AgentLoginBean agent_login;
    private AdConfigBean ad_config;

    public String getAd_tx_jili() {
        return ad_tx_jili;
    }

    public void setAd_tx_jili(String ad_tx_jili) {
        this.ad_tx_jili = ad_tx_jili;
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

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
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

    public static class AgentLoginBean {
        /**
         * id : 1
         * agent_id : 1
         * agent_ads :
         * app_type : 1
         * share_img :
         * share_url :
         * shield_area :
         * bd_ak :
         */

        private int id;
        private String agent_id;
        private String agent_ads;
        private int app_type;
        private String share_img;
        private String share_url;
        private String shield_area;
        private String bd_ak;

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

        public String getShield_area() {
            return shield_area;
        }

        public void setShield_area(String shield_area) {
            this.shield_area = shield_area;
        }

        public String getBd_ak() {
            return bd_ak;
        }

        public void setBd_ak(String bd_ak) {
            this.bd_ak = bd_ak;
        }
    }

    public static class AdConfigBean {
        /**
         * jili : {"csj":{"num":"5","sort":"2"},"tx":{"num":"4","sort":"1"}}
         */

        private JiliBean jili;

        public JiliBean getJili() {
            return jili;
        }

        public void setJili(JiliBean jili) {
            this.jili = jili;
        }

        public static class JiliBean {
            /**
             * csj : {"num":"5","sort":"2"}
             * tx : {"num":"4","sort":"1"}
             */

            private CsjBean csj;
            private TxBean tx;

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
                 * num : 5
                 * sort : 2
                 */

                private String num;
                private String sort;

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
                 * num : 4
                 * sort : 1
                 */

                private String num;
                private String sort;

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
}
