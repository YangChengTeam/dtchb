package com.yc.redkingguess.homeModule.widget.selectrecyclerview;

import java.util.ArrayList;

/**
 * Created by yushuangping on 2018/8/23.
 */

public class HotelEntity {

    public ArrayList<TagsEntity> allTagsList;

    public ArrayList<TagsEntity> getAllTagsList() {
        return allTagsList;
    }

    public void setAllTagsList(ArrayList<TagsEntity> allTagsList) {
        this.allTagsList = allTagsList;
    }

    public static class TagsEntity {
        public String tagsName;
        public ArrayList<TagInfo> tagInfoList;

        public String getTagsName() {
            return tagsName;
        }

        public void setTagsName(String tagsName) {
            this.tagsName = tagsName;
        }

        public ArrayList<TagInfo> getTagInfoList() {
            return tagInfoList;
        }

        public void setTagInfoList(ArrayList<TagInfo> tagInfoList) {
            this.tagInfoList = tagInfoList;
        }

        public static class TagInfo {
            public String tagName;
            public String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }
        }
    }

}
