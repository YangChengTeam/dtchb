package com.yc.jsdps.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.jsdps.R;
import com.yc.jsdps.base.BaseActivity;
import com.yc.jsdps.beans.contact.HelpQuestionContact;
import com.yc.jsdps.beans.module.beans.HelpQuestionBeans;
import com.yc.jsdps.beans.present.HelpQuestionPresenter;
import com.yc.jsdps.beans.widget.selectrecyclerview.HotelEntity;
import com.yc.jsdps.beans.widget.selectrecyclerview.HotelEntityAdapter;
import com.yc.jsdps.utils.CacheDataUtils;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;

public class HelpQuestionActivity extends BaseActivity<HelpQuestionPresenter> implements HelpQuestionContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HotelEntityAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_help_question;
    }

    public static void helpJump(Context context){
        Intent intent=new Intent(context,HelpQuestionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initEventAndData() {
        setTitle("玩法攻略");
        initRecyclerView();
        mPresenter.getaboutplayList(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    private void initRecyclerView() {
        mAdapter = new HotelEntityAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(new HotelEntityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int section, int position) {


            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void getaboutplayListSuccess(List<HelpQuestionBeans> data) {
        if (data!=null&&data.size()>0){
            HotelEntity entity=new HotelEntity();
            ArrayList<HotelEntity.TagsEntity> allTagsList=new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                HotelEntity.TagsEntity tagsEntity16=new HotelEntity.TagsEntity();
                HotelEntity.TagsEntity.TagInfo tagInfo16=new HotelEntity.TagsEntity.TagInfo();
                tagInfo16.setTagName(data.get(i).getAnswer());
                tagInfo16.setTitle("");
                tagsEntity16.setTagsName(data.get(i).getQuestion());
                ArrayList<HotelEntity.TagsEntity.TagInfo> tagInfoList16=new ArrayList<>();
                tagInfoList16.add(tagInfo16);
                tagsEntity16.setTagInfoList(tagInfoList16);
                allTagsList.add(tagsEntity16);
            }
            entity.setAllTagsList(allTagsList);
            mAdapter.setData(entity.getAllTagsList());
            mAdapter.notifyDataSetChanged();
        }
    }
}