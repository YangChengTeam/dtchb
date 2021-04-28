package com.yc.majiaredgrab.homeModule.module.bean;


public class AdCodeBeans {

    /**
     * ad_banner : 945780274
     * ad_jili : 945634637
     * ad_express : 945649449
     * ad_insert : 945710087
     * ad_kaiping : 887408659
     * server_ip : 119.23.173.220
     */
    private String share_img;
    private String ad_banner;
    private String ad_jili;
    private String ad_express;
    private String ad_insert;
    private String ad_kaiping;
    private String server_ip;
    private String agent_login;
    private String agent_ads;
    private String ad_tx_jili;
    private int app_type;

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

    public String getAd_tx_jili() {
        return ad_tx_jili;
    }

    public void setAd_tx_jili(String ad_tx_jili) {
        this.ad_tx_jili = ad_tx_jili;
    }

    private AdConfigBean ad_config;

    public AdConfigBean getAd_config() {
        return ad_config;
    }

    public void setAd_config(AdConfigBean ad_config) {
        this.ad_config = ad_config;
    }

    public String getAgent_ads() {
        return agent_ads;
    }

    public void setAgent_ads(String agent_ads) {
        this.agent_ads = agent_ads;
    }

    public String getAgent_login() {
        return agent_login;
    }

    public void setAgent_login(String agent_login) {
        this.agent_login = agent_login;
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

    public static class AdConfigBean {
        /**
         * kaiping : []
         * jili : {"csj":{"num":"3","sort":"2"},"tx":{"num":"4","sort":"1"}}
         */

        private AdConfigBean.JiliBean jili;


        public AdConfigBean.JiliBean getJili() {
            return jili;
        }

        public void setJili(AdConfigBean.JiliBean jili) {
            this.jili = jili;
        }

        public static class JiliBean {
            /**
             * csj : {"num":"3","sort":"2"}
             * tx : {"num":"4","sort":"1"}
             */

            private AdConfigBean.JiliBean.CsjBean csj;
            private AdConfigBean.JiliBean.TxBean tx;

            public AdConfigBean.JiliBean.CsjBean getCsj() {
                return csj;
            }

            public void setCsj(AdConfigBean.JiliBean.CsjBean csj) {
                this.csj = csj;
            }

            public AdConfigBean.JiliBean.TxBean getTx() {
                return tx;
            }

            public void setTx(AdConfigBean.JiliBean.TxBean tx) {
                this.tx = tx;
            }

            public static class CsjBean {
                /**
                 * num : 3
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
